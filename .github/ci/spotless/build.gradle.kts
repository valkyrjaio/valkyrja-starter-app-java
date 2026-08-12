/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

import io.valkyrja.spotless.CopyrightHeader

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("io.valkyrja:ci-spotless:26.1.8")
    }
}

plugins {
    id("com.diffplug.spotless") version "8.9.0"
    id("com.github.ben-manes.versions") version "0.61.0"
    id("se.patrikerdes.use-latest-versions") version "0.2.19"
}

group = "io.valkyrja"
version = "1.0.0"

repositories {
    mavenCentral()
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

spotless {
    java {
        // The JUnit, sindri-JUnit and ArchUnit builds hold the app's other Java source trees;
        // format them too. Each is scoped to `src/test/java` so any non-source .java under
        // `src/test/resources` is never rewritten.
        target(
            "app/src/**/*.java",
            ".github/ci/junit/src/test/java/**/*.java",
            ".github/ci/sindri-junit/src/test/java/**/*.java",
            ".github/ci/archunit/src/test/java/**/*.java",
        )
        targetExclude("**/*.example.java")
        googleJavaFormat("1.27.0").aosp()
        licenseHeader(CopyrightHeader.block("Valkyrja Application"))
    }

    // The two entry point scripts have no extension, so the `**/*.java` target above cannot
    // reach them, and no other tool in the gate reads them. Their header went unchecked for
    // that reason. This format holds them to the same header, in shell comment syntax.
    // Warning: the license step replaces everything before the delimiter. The delimiter
    // therefore matches the first line that starts with neither a comment mark nor a blank,
    // and `skipLinesMatching` holds the shebang on line 1, which the header follows.
    format("shell") {
        target("app/bin/cli", "app/public/index")
        licenseHeader(CopyrightHeader.shell("Valkyrja Application"), "(?=[^#\\s])").skipLinesMatching("^#!.*\$")
    }
}
