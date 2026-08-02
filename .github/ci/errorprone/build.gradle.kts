/*
 * This file is part of the Valkyrja Application package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

import net.ltgt.gradle.errorprone.CheckSeverity
import net.ltgt.gradle.errorprone.errorprone

plugins {
    java
    id("net.ltgt.errorprone") version "5.1.0"
    id("com.github.ben-manes.versions") version "0.58.0"
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
    implementation("io.valkyrja:valkyrja:26.5.1")

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
    errorprone("com.google.errorprone:error_prone_core:2.50.0")
    errorprone("com.uber.nullaway:nullaway:0.13.8")

    // Mirrors the JUnit build's test classpath — needed only so the tests compile here.
    testImplementation("io.valkyrja:valkyrja:26.5.1")
    testImplementation("org.junit.jupiter:junit-jupiter:6.1.2")
    testImplementation("org.mockito:mockito-core:5.23.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.23.0")
    testImplementation("org.jspecify:jspecify:1.0.1")
    testImplementation("io.grpc:grpc-api:1.83.1")
    testImplementation("io.grpc:grpc-servlet-jakarta:1.83.1")
    testImplementation("io.grpc:grpc-netty-shaded:1.83.1")
    testImplementation("org.eclipse.jetty:jetty-server:12.1.11")
    testImplementation("org.eclipse.jetty.ee10:jetty-ee10-servlet:12.1.11")
    testImplementation("io.netty:netty-codec-http:4.2.16.Final")
    testImplementation("org.apache.tomcat.embed:tomcat-embed-core:11.0.24")
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

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.errorprone {
        check("NullAway", CheckSeverity.ERROR)
        option("NullAway:AnnotatedPackages", "app")
    }
}

// Compiling the tests is the point — running them is the JUnit build's job, so `build` compiles
// the test sources (Error Prone runs as part of that) without executing the suite twice.
tasks.test {
    enabled = false
}

tasks.named("check") {
    dependsOn(tasks.compileTestJava)
}

// NullAway enforces a nullness contract on the app's own API. Tests deliberately break it to reach
// defensive guards, so it is scoped to `app/src`; every other Error Prone check still applies to
// the test tree.
tasks.compileTestJava {
    options.errorprone {
        check("NullAway", CheckSeverity.OFF)
    }
}
