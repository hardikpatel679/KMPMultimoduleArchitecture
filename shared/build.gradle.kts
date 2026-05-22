import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
            // Export dependencies so iOS can see them if needed
            export(project(":domain"))
            export(project(":core"))
            export(project(":data"))
            export(project(":feature:login"))
            export(project(":feature:dashboard"))
        }
    }

    android {
       namespace = "com.hdapp.myapplication.shared"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()

       androidResources {
           enable = true
       }

       withDeviceTest {
           instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
       }

       withHostTest {}

       compilerOptions {
           jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
       }
    }
    
    sourceSets {
        commonMain.dependencies {
            api(project(":domain"))
            api(project(":core"))
            api(project(":data"))
            api(project(":feature:login"))
            api(project(":feature:dashboard"))
            
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.androidx.hilt.navigation.compose)
            implementation(libs.hilt.android)
        }
        val androidDeviceTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.compose.test.ui)
                implementation(libs.compose.test.manifest)
            }
        }
        val androidHostTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.androidx.testExt.junit)
            }
        }
    }
}

dependencies {
    add("kspAndroid", libs.hilt.compiler)
    add("androidRuntimeClasspath", libs.compose.test.manifest)
}
