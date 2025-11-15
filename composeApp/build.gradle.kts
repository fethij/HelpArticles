import com.tewelde.articles.addKspDependencyForAllTargets
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.articles.featureMultiplatform)
}

kotlin {
    jvmToolchain(libs.versions.jvmTarget.get().toInt())

    androidTarget {}

    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.kotlinx.coroutines.android)
            api(libs.androidx.work.runtime.ktx)
        }

        commonMain.dependencies {
            implementation(projects.core.connectivity)
            implementation(projects.core.navigation)
            implementation(projects.core.data)
            implementation(projects.core.database)
            implementation(projects.core.domain)
            implementation(projects.core.network)
            implementation(projects.core.sync)

            implementation(projects.feature.articles)
            implementation(projects.feature.detail)

            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.bundles.circuit)
            implementation(libs.bundles.kotlinInjectAnvil)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

addKspDependencyForAllTargets(libs.circuit.codegen)
addKspDependencyForAllTargets(libs.kotlinInject.compiler)
addKspDependencyForAllTargets(libs.kotlinInject.anvil.compiler)

android {
    namespace = "com.tewelde.articles"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.tewelde.helparticles"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 5
        versionName = "1.0.5"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
        coreLibraryDesugaring(libs.desugar.jdk.libs)
    }

    testOptions {
        unitTests {
            all {
                it.exclude("**/screen/**")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.tewelde.articles"
            packageVersion = "1.0.0"
        }
    }
}