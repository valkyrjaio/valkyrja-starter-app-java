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

rootProject.name = "errorprone"

// The framework is resolved from Maven Central (io.valkyrja:valkyrja). To build against a local
// framework checkout instead, opt in with -PlocalFramework. Off by default, so CI always builds
// against the published release. Mirrors the root settings.gradle.kts.
if (providers.gradleProperty("localFramework").isPresent) {
    includeBuild("../../../../valkyrja")
}
