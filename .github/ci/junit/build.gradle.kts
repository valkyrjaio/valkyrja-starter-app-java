/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

plugins {
    java
    jacoco
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
}

dependencies {
    // The JaCoCo tool version is declared here rather than left to the plugin default (or set via
    // `jacoco { toolVersion }`) so it is a real dependency notation. useLatestVersions only
    // rewrites dependency notations, so an implicit or toolVersion-pinned tool is reported as
    // outdated every run but never updated — it drifts forever.
    jacocoAgent("org.jacoco:org.jacoco.agent:0.8.15")
    jacocoAnt("org.jacoco:org.jacoco.ant:0.8.15")

    implementation("io.valkyrja:valkyrja:26.10.1")
    compileOnly("org.jspecify:jspecify:1.0.1")

    // Runtime SDKs for the worker entry points. The framework declares them compileOnly, so the
    // app's own build supplies them; the end-to-end tests start these servers for real.
    implementation("org.eclipse.jetty:jetty-server:12.1.12")
    implementation("io.netty:netty-codec-http:4.2.17.Final")
    implementation("org.apache.tomcat.embed:tomcat-embed-core:11.0.25")

    // gRPC transports for the gRPC worker entry points (app.grpc.{Jetty,Netty,Tomcat}App). The
    // framework keeps io.grpc compileOnly, so the application supplies the transport it uses;
    // the servlet transport also needs Jetty's ee10 servlet layer.
    implementation("io.grpc:grpc-api:1.84.0")
    implementation("io.grpc:grpc-servlet-jakarta:1.84.0")
    implementation("io.grpc:grpc-netty-shaded:1.84.0")
    implementation("org.eclipse.jetty.ee10:jetty-ee10-servlet:12.1.12")
    testImplementation("org.junit.jupiter:junit-jupiter:6.1.3")
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

    // Exclude the server entry points. app.http.App / app.http.CgiApp extend the framework's
    // ExchangeHttp / ExchangeCgiHttp bootstraps, whose run() starts non-daemon server threads that
    // cannot be exercised from a unit test without leaking the server / hanging the test JVM (the
    // framework excludes ExchangeHttp / ExchangeCgiHttp for the same reason). app.grpc.App is a
    // scaffold whose main() only bootstraps and returns — a transport adapter (Netty/Tomcat/Jetty)
    // is attached separately to actually serve — so a full cache bootstrap is integration-level.
                    // The jetty/netty/tomcat entries are the same shape: a main() that blocks on
                    // its runtime's server loop. Their live request path is covered end to end by
                    // app.tests.functional.entry.*AppTest, which starts each real server on a free port.
// Shared by jacocoTestReport and jacocoTestCoverageVerification so the gate measures exactly
// what the report shows — scoping only one of them silently lets the other disagree.
val coverageExclusions = listOf(
        "**/app/http/App.class",
        "**/app/http/CgiApp.class",
        "**/app/grpc/App.class",
        "**/app/grpc/JettyApp.class",
        "**/app/grpc/NettyApp.class",
        "**/app/grpc/TomcatApp.class",
        "**/app/http/JettyApp.class",
        "**/app/http/NettyApp.class",
        "**/app/http/TomcatApp.class",
)

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    classDirectories.setFrom(
            classDirectories.files.map { dir ->
                fileTree(dir) { exclude(coverageExclusions) }
            })
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

// The floor. Coverage was reported here and enforced nowhere: `junit` ran `test` finalized by
// `jacocoTestReport`, so the report was generated and then nothing asserted anything about it.
tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.test)
    classDirectories.setFrom(
            classDirectories.files.map { dir ->
                fileTree(dir) { exclude(coverageExclusions) }
            })
    violationRules {
        rule {
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "1.00".toBigDecimal()
            }
            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = "1.00".toBigDecimal()
            }
        }
        // Per class as well as per bundle. A bundle-wide rule is not enough: a large,
        // well-covered codebase absorbs one entirely untested new class almost without moving,
        // so the aggregate stays high while the new file is at zero. A class-level rule fails
        // on that file itself.
        rule {
            element = "CLASS"
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "1.00".toBigDecimal()
            }
            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = "1.00".toBigDecimal()
            }
        }
    }
}
