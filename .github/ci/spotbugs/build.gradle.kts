/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort
import com.github.spotbugs.snom.SpotBugsTask

plugins {
    java
    id("com.github.spotbugs") version "6.5.10"
    id("com.github.ben-manes.versions") version "0.61.0"
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
    // The JUnit build's tests are the app's other Java source tree; analyze them too. The
    // sindri-junit tests are deliberately not included: they assert on classes produced by a
    // codegen step, so compiling them here would mean replicating that pipeline.
    test {
        java {
            srcDirs("../junit/src/test/java")
        }
    }
}

dependencies {
    // The SpotBugs tool version is declared here rather than via `spotbugs { toolVersion }` so it
    // is a real dependency notation. useLatestVersions only rewrites dependency notations, so a
    // toolVersion string is reported as outdated every run but never updated — it drifts forever.
    spotbugs("com.github.spotbugs:spotbugs:4.10.3")

    implementation("io.valkyrja:valkyrja:26.8.2")

    // Runtime SDKs for the worker entry points (app.http.{Jetty,Netty,Tomcat}App).
    // The framework declares them compileOnly, so the app supplies them.
    implementation("org.eclipse.jetty:jetty-server:12.1.12")
    implementation("io.netty:netty-codec-http:4.2.17.Final")
    implementation("org.apache.tomcat.embed:tomcat-embed-core:11.0.24")

    // gRPC transports for the gRPC worker entry points (app.grpc.{Jetty,Netty,Tomcat}App). The
    // framework keeps io.grpc compileOnly, so the application supplies the transport it uses;
    // the servlet transport also needs Jetty's ee10 servlet layer.
    implementation("io.grpc:grpc-api:1.83.1")
    implementation("io.grpc:grpc-servlet-jakarta:1.83.1")
    implementation("io.grpc:grpc-netty-shaded:1.83.1")
    implementation("org.eclipse.jetty.ee10:jetty-ee10-servlet:12.1.12")
    compileOnly("org.jspecify:jspecify:1.0.1")

    // Mirrors the JUnit build's test classpath — needed only so the tests compile here.
    testImplementation("io.valkyrja:valkyrja:26.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter:6.1.3")
    testImplementation("org.mockito:mockito-core:5.23.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.23.0")
    testImplementation("org.jspecify:jspecify:1.0.1")
    testImplementation("io.grpc:grpc-api:1.83.1")
    testImplementation("io.grpc:grpc-servlet-jakarta:1.83.1")
    testImplementation("io.grpc:grpc-netty-shaded:1.83.1")
    testImplementation("org.eclipse.jetty:jetty-server:12.1.12")
    testImplementation("org.eclipse.jetty.ee10:jetty-ee10-servlet:12.1.12")
    testImplementation("io.netty:netty-codec-http:4.2.17.Final")
    testImplementation("org.apache.tomcat.embed:tomcat-embed-core:11.0.24")
}

spotbugs {
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

// Analyzing the tests is the point — running them is the JUnit build's job, so `check` still runs
// spotbugsTest without executing the suite twice.
tasks.test {
    enabled = false
}

// The test tree gets its own filter so `app/src` stays strict — the JUnit idioms excluded for the
// tests can never loosen the app's own analysis.
tasks.spotbugsTest {
    excludeFilter.set(layout.projectDirectory.file("spotbugs-exclude-test.xml"))
}
