/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    java
    jacoco
    id("com.github.ben-manes.versions") version "0.56.0"
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

// Sindri (the AST code generator) plus the framework sources jar it needs to resolve providers.
// Unlike the sibling `junit` build — which copies the empty `*.example.java` stubs and tests the
// application as it compiles *without* generation — this build runs Sindri to produce the real
// App*Data classes and tests the application as it actually runs in production. It is intentionally
// a separate, isolated CI environment so stubbed and generated results never mingle, and it doubles
// as a way to test Sindri itself against a real application before a framework release.
val sindri by configurations.creating

sourceSets {
    main {
        java {
            srcDirs("../../../app/src/main/java")
            exclude("**/*.example.java")
        }
    }
}

dependencies {
    // The JaCoCo tool version is declared here rather than left to the plugin default (or set via
    // `jacoco { toolVersion }`) so it is a real dependency notation. useLatestVersions only
    // rewrites dependency notations, so an implicit or toolVersion-pinned tool is reported as
    // outdated every run but never updated — it drifts forever.
    jacocoAgent("org.jacoco:org.jacoco.agent:0.8.15")
    jacocoAnt("org.jacoco:org.jacoco.ant:0.8.15")

    implementation("io.valkyrja:valkyrja:26.4.0")
    compileOnly("org.jspecify:jspecify:1.0.0")

    // Runtime SDKs for the worker entry points. The framework declares them compileOnly, so the
    // app supplies them; the end-to-end tests start each of these servers for real.
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
    sindri("io.valkyrja:sindri:26.4.2")
    sindri("io.valkyrja:valkyrja:26.4.0:sources")
    testImplementation("org.junit.jupiter:junit-jupiter:6.1.2")
    testImplementation("org.mockito:mockito-core:5.23.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.23.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

val appDir = file("../../../app")

val sindriConfigs =
    mapOf(
        "Http" to "src/main/java/app/http/Config.java",
        "Cli" to "src/main/java/app/cli/Config.java",
        "Grpc" to "src/main/java/app/grpc/Config.java",
    )

val sindriTasks =
    sindriConfigs.map { (name, configPath) ->
        tasks.register<JavaExec>("sindri$name") {
            group = "sindri"
            description = "Generate the $name App*Data files via sindri for the generated-class tests"
            classpath = sindri
            mainClass.set("io.sindri.Sindri")
            // Sindri reads System.getProperty("user.dir") and writes the data relative to it.
            workingDir = appDir
            args("generate", configPath)
        }
    }

tasks.named<JavaCompile>("compileJava") {
    dependsOn(sindriTasks)
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates") {
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
    // Exclude the HTTP server entry points: app.http.App / app.http.CgiApp extend the framework's
    // ExchangeHttp / ExchangeCgiHttp bootstraps, whose run() starts non-daemon server threads that
    // cannot be exercised from a unit test without leaking the server / hanging the test JVM.
    classDirectories.setFrom(
            classDirectories.files.map { dir ->
                fileTree(dir) {
                    exclude("**/app/http/App.class")
                    exclude("**/app/http/CgiApp.class")
                    // The jetty/netty/tomcat entries are the same shape: a main() that blocks on
                    // its runtime's server loop. Their live request path is covered end to end by
                    // app.tests.functional.entry.*AppTest, which starts each real server on a free port.
                    exclude("**/app/http/JettyApp.class")
                    exclude("**/app/http/NettyApp.class")
                    exclude("**/app/http/TomcatApp.class")
                    exclude("**/app/grpc/App.class")
                    exclude("**/app/grpc/JettyApp.class")
                    exclude("**/app/grpc/NettyApp.class")
                    exclude("**/app/grpc/TomcatApp.class")
                }
            })
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}
