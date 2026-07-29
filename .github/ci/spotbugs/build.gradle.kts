/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort
import com.github.spotbugs.snom.SpotBugsTask

plugins {
    java
    id("com.github.spotbugs") version "6.5.9"
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

    // Runtime SDKs for the worker entry points (app.http.{Jetty,Netty,Tomcat}App).
    // The framework declares them compileOnly, so the app supplies them.
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
    compileOnly("org.jspecify:jspecify:1.0.0")
}

spotbugs {
    toolVersion.set("4.9.8")
    excludeFilter.set(layout.projectDirectory.file("spotbugs-exclude.xml"))
    effort.set(Effort.MAX)
    reportLevel.set(Confidence.LOW)
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

tasks.withType<SpotBugsTask>().configureEach {
    reports.create("html")
}
