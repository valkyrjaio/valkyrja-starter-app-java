/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

plugins {
    java
    jacoco
    id("com.github.ben-manes.versions") version "0.54.0"
    id("se.patrikerdes.use-latest-versions") version "0.2.19"
}

group = "io.valkyrja"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

sourceSets {
    main {
        java {
            srcDirs("../../../app/src/main/java")
            exclude("**/*.example.java")
        }
    }
}

dependencies {
    implementation("io.valkyrja:valkyrja:26.4.0")
    compileOnly("org.jspecify:jspecify:1.0.0")

    // Runtime SDKs for the worker entry points. The framework declares them compileOnly, so the
    // app's own build supplies them; the end-to-end tests start these servers for real.
    implementation("org.eclipse.jetty:jetty-server:12.1.11")
    implementation("io.netty:netty-codec-http:4.2.16.Final")
    implementation("org.apache.tomcat.embed:tomcat-embed-core:11.0.24")

    // gRPC transports for the gRPC worker entry points (app.grpc.{Jetty,Netty,Tomcat}App). The
    // framework keeps io.grpc compileOnly, so the application supplies the transport it uses;
    // the servlet transport also needs Jetty's ee10 servlet layer.
    implementation("io.grpc:grpc-api:1.83.0")
    implementation("io.grpc:grpc-servlet-jakarta:1.83.0")
    implementation("io.grpc:grpc-netty-shaded:1.83.0")
    implementation("org.eclipse.jetty.ee10:jetty-ee10-servlet:12.1.11")
    testImplementation("org.junit.jupiter:junit-jupiter:6.1.2")
    testImplementation("org.mockito:mockito-core:5.23.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.23.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates") {
    rejectVersionIf { isNonStable(candidate.version) }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    // Exclude the server entry points. app.http.App / app.http.CgiApp extend the framework's
    // ExchangeHttp / ExchangeCgiHttp bootstraps, whose run() starts non-daemon server threads that
    // cannot be exercised from a unit test without leaking the server / hanging the test JVM (the
    // framework excludes ExchangeHttp / ExchangeCgiHttp for the same reason). app.grpc.App is a
    // scaffold whose main() only bootstraps and returns — a transport adapter (Netty/Tomcat/Jetty)
    // is attached separately to actually serve — so a full cache bootstrap is integration-level.
    classDirectories.setFrom(
            classDirectories.files.map { dir ->
                fileTree(dir) {
                    exclude("**/app/http/App.class")
                    exclude("**/app/http/CgiApp.class")
                    exclude("**/app/grpc/App.class")
                    exclude("**/app/grpc/JettyApp.class")
                    exclude("**/app/grpc/NettyApp.class")
                    exclude("**/app/grpc/TomcatApp.class")
                    // The jetty/netty/tomcat entries are the same shape: a main() that blocks on
                    // its runtime's server loop. Their live request path is covered end to end by
                    // app.functional.entry.*AppTest, which starts each real server on a free port.
                    exclude("**/app/http/JettyApp.class")
                    exclude("**/app/http/NettyApp.class")
                    exclude("**/app/http/TomcatApp.class")
                }
            })
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}
