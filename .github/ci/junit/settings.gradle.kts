/*
 * This file is part of the Valkyrja Application package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
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
