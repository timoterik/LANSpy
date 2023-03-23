/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and
 * is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution,
 * or other use of DCCTech's intellectual property without prior written consent is strictly prohibited.
 *
 */

import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "io.dcctech.lan.spy.desktop"
version = "2023.3.10"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.materialIconsExtended)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

compose.desktop {
    application {
        mainClass = "io.dcctech.lan.spy.desktop.Application.kt"
        nativeDistributions {
            //https://github.com/JetBrains/compose-jb/blob/master/tutorials/Native_distributions_and_local_execution/README.md
            javaHome = System.getenv("JDK_17")
            packageName = "LANSpy-desktop"
            packageVersion = "1.0.0"
            copyright =
                "A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and " +
                        "is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, " +
                        "or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. "
            appResourcesRootDir.set(project.layout.projectDirectory.dir("resources"))
            nativeDistributions {
                targetFormats(TargetFormat.Exe, TargetFormat.Rpm, TargetFormat.Msi, TargetFormat.Deb)
                macOS {
                    dockName = "LANSpy"
                    setDockNameSameAsPackageName = false
                    iconFile.set(project.file("src/main/resources/Color-dcctech-no-bg.icns"))
                }
                windows {
                    menuGroup = "DCCTech"
                    menu = true
                    iconFile.set(project.file("src/main/resources/Color-dcctech-no-bg.ico"))
                    console = true
                    shortcut = true
                }
                linux {
                    menuGroup = "DCCTech"
                    iconFile.set(project.file("src/main/resources/Color-dcctech-no-bg.png"))
                    shortcut = true
                }

            }
        }
    }
}