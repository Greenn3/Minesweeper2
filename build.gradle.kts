import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    kotlin("multiplatform") version "1.9.20" // Latest stable Kotlin version (check compatibility)
    id("org.jetbrains.compose") version "1.5.11" // Latest stable Compose Multiplatform version
}

group = "me.asia"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    js(IR) {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    useFirefox()
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(compose.html.core) // Updated from compose.web.core
                implementation(compose.runtime)
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

// Remove outdated workaround, Webpack should work fine in newer versions
