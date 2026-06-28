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
    implementation("io.valkyrja:valkyrja:26.1.2")
    compileOnly("org.jspecify:jspecify:1.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter:6.1.1")
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
    // Exclude the HTTP server entry points: app.http.App / app.http.CgiApp extend the framework's
    // ExchangeHttp / ExchangeCgiHttp bootstraps, whose run() starts non-daemon server threads that
    // cannot be exercised from a unit test without leaking the server / hanging the test JVM (the
    // framework excludes ExchangeHttp / ExchangeCgiHttp for the same reason).
    classDirectories.setFrom(
            classDirectories.files.map { dir ->
                fileTree(dir) {
                    exclude("**/app/http/App.class")
                    exclude("**/app/http/CgiApp.class")
                }
            })
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}
