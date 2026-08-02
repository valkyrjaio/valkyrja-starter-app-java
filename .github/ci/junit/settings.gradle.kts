/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "junit"

// The framework is resolved from Maven Central (io.valkyrja:valkyrja). To run the tests against a
// local framework checkout instead, opt in with -PlocalFramework (e.g. ./gradlew junit
// -PlocalFramework, or set localFramework=true in your local gradle.properties). Off by default,
// so CI always tests against the published release. Mirrors the root settings.gradle.kts.
if (providers.gradleProperty("localFramework").isPresent) {
    includeBuild("../../../../valkyrja")
}
