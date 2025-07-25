import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

operator fun KotlinSourceSet.invoke(builder: SourceSetHierarchyBuilder.() -> Unit): KotlinSourceSet {
    SourceSetHierarchyBuilder(this).builder()
    return this
}

class SourceSetHierarchyBuilder(private val node: KotlinSourceSet) {
    operator fun KotlinSourceSet.unaryMinus() = this.dependsOn(node)
}

plugins {
    kotlin("multiplatform")
    `maven-publish`
}

version = "1.0.0-SNAPSHOT"

publishing {
    repositories {
        maven("<localRepo>")
    }
}

kotlin {
    jvm()

    linuxX64()
    linuxArm64()

    macosX64("macos")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    mingwX64("windowsX64")

    val commonMain by sourceSets.getting
    val concurrentMain by sourceSets.creating
    val jvmMain by sourceSets.getting
    val nativeMain by sourceSets.creating
    val appleAndLinuxMain by sourceSets.creating
    val linuxMain by sourceSets.creating
    val linuxX64Main by sourceSets.getting
    val linuxArm64Main by sourceSets.getting
    val appleMain by sourceSets.creating
    val macosMain by sourceSets.getting
    val iosMain by sourceSets.creating
    val iosX64Main by sourceSets.getting
    val iosArm64Main by sourceSets.getting
    val iosSimulatorArm64Main by sourceSets.getting
    val windowsX64Main by sourceSets.getting

    commonMain {
        -concurrentMain {
            -jvmMain
            -nativeMain {
                -appleAndLinuxMain {
                    -appleMain {
                        -iosMain {
                            -iosX64Main
                            -iosArm64Main
                            -iosSimulatorArm64Main
                        }
                        -macosMain
                    }
                    -linuxMain {
                        -linuxArm64Main
                        -linuxX64Main
                    }
                }

                -windowsX64Main
            }
        }
    }

    targets.withType<KotlinNativeTarget>().configureEach {
        compilations.getByName("main").cinterops.create("withPosix") {
            this.packageName = "withPosix"
            header(file("libs/withPosix.h"))
        }
        compilations.getByName("main").cinterops.create("simple") {
            header(file("libs/simple.h"))
        }
    }

    sourceSets.all {
        languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
    }
}
