/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

plugins {
    java
    id("com.github.ben-manes.versions") version "0.59.0"
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
    // The JUnit build's tests, compiled but never executed — ArchUnit reads the resulting
    // bytecode. Kept out of the `test` source set so ArchitectureTest's classpath scan keeps
    // seeing the app source only: the test tree has its own taxonomy and is checked by
    // TestArchitectureTest, which imports it by path.
    create("testTree") {
        java {
            srcDirs("../junit/src/test/java")
        }
        compileClasspath += sourceSets["main"].output
    }
}

dependencies {
    implementation("io.valkyrja:valkyrja:26.6.0")

    // Runtime SDKs for the worker entry points (app.http.{Jetty,Netty,Tomcat}App).
    // The framework declares them compileOnly, so the app supplies them.
    implementation("org.eclipse.jetty:jetty-server:12.1.11")
    implementation("io.netty:netty-codec-http:4.2.16.Final")
    implementation("org.apache.tomcat.embed:tomcat-embed-core:11.0.24")

    // gRPC transports for the gRPC worker entry points (app.grpc.{Jetty,Netty,Tomcat}App). The
    // framework keeps io.grpc compileOnly, so the application supplies the transport it uses;
    // the servlet transport also needs Jetty's ee10 servlet layer.
    implementation("io.grpc:grpc-api:1.83.1")
    implementation("io.grpc:grpc-servlet-jakarta:1.83.1")
    implementation("io.grpc:grpc-netty-shaded:1.83.1")
    implementation("org.eclipse.jetty.ee10:jetty-ee10-servlet:12.1.11")
    compileOnly("org.jspecify:jspecify:1.0.1")
    testImplementation("com.tngtech.archunit:archunit-junit5:1.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter:6.1.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Mirrors the JUnit build's test classpath — needed only so the tests compile here. The app's
    // own dependencies are repeated because a custom source set does not inherit `main`'s.
    "testTreeImplementation"("io.valkyrja:valkyrja:26.6.0")
    "testTreeImplementation"("org.junit.jupiter:junit-jupiter:6.1.2")
    "testTreeImplementation"("org.mockito:mockito-core:5.23.0")
    "testTreeImplementation"("org.mockito:mockito-junit-jupiter:5.23.0")
    "testTreeImplementation"("org.jspecify:jspecify:1.0.1")
    "testTreeImplementation"("io.grpc:grpc-api:1.83.1")
    "testTreeImplementation"("io.grpc:grpc-servlet-jakarta:1.83.1")
    "testTreeImplementation"("io.grpc:grpc-netty-shaded:1.83.1")
    "testTreeImplementation"("org.eclipse.jetty:jetty-server:12.1.11")
    "testTreeImplementation"("org.eclipse.jetty.ee10:jetty-ee10-servlet:12.1.11")
    "testTreeImplementation"("io.netty:netty-codec-http:4.2.16.Final")
    "testTreeImplementation"("org.apache.tomcat.embed:tomcat-embed-core:11.0.24")
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
    dependsOn("testTreeClasses")

    val testTreeClasses = sourceSets["testTree"].output.classesDirs

    // The test tree is read by path, not off the test classpath, so Gradle cannot infer that it
    // affects this task. Without declaring it, `test` stays UP-TO-DATE when only the tests change
    // and TestArchitectureTest silently passes against stale bytecode.
    inputs.files(testTreeClasses)
            .withPropertyName("testTreeClasses")
            .withPathSensitivity(PathSensitivity.RELATIVE)

    // Where TestArchitectureTest imports the compiled test tree from.
    systemProperty("app.testTreeClasses", testTreeClasses.asPath)
}
