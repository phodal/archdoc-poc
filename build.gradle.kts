plugins {
    kotlin("multiplatform") version "1.6.20"
    application
}

group = "org.archguard"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-jupyter-api-gradle-plugin:0.11.0-84")
    }
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            apply(plugin = "org.jetbrains.kotlin.jupyter.api")
            dependencies {
//                implementation("org.jetbrains.kotlinx:kotlin-jupyter-shared-compiler:0.11.0-84")
//                implementation("org.jetbrains.kotlinx:kotlin-jupyter-api:0.11.0-84")

                implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.6.20")
                implementation("org.jetbrains.kotlin:kotlin-scripting-compiler-impl:1.6.20")
                implementation("org.jetbrains.kotlin:kotlin-scripting-compiler-impl-embeddable:1.6.20")
                implementation("org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable:1.6.20")
                implementation("org.jetbrains.kotlin:kotlin-scripting-ide-services:1.6.20")
                implementation("org.jetbrains.kotlin:kotlin-scripting-dependencies:1.6.20")
                implementation("org.jetbrains.kotlin:kotlin-scripting-dependencies-maven-all:1.6.20")
                implementation("org.jetbrains.kotlin:kotlin-script-util:1.6.20")
                implementation("org.jetbrains.kotlin:kotlin-scripting-common:1.6.20")
                implementation("org.jetbrains.kotlin:kotlin-scripting-jvm:1.6.20")
                implementation("org.jetbrains.kotlin:kotlin-script-runtime:1.6.20")

                implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.20")
                implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.20")
                implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.20")

                implementation("io.ktor:ktor-server-netty:1.6.7")
                implementation("io.ktor:ktor-html-builder:1.6.7")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("io.kotest:kotest-assertions-core:5.1.0")
            }

        }
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:17.0.2-pre.290-kotlin-1.6.10")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:17.0.2-pre.290-kotlin-1.6.10")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-css:17.0.2-pre.290-kotlin-1.6.10")
            }
        }
        val jsTest by getting
    }
}

application {
    mainClass.set("org.archguard.application.ServerKt")
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}