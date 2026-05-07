/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

subprojects {
    group = "com.example"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }

    plugins.withId("java") {
        dependencies {
            "implementation"("io.valkyrja:valkyrja:26.0.0")
        }

        extensions.configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(21))
            }
        }

        tasks.withType<JavaCompile> {
            options.encoding = "UTF-8"
        }
    }
}