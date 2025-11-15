plugins {
    alias(libs.plugins.articles.kotlinMultiplatform)
    alias(libs.plugins.articles.featureMultiplatform)
    alias(libs.plugins.articles.composeMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.connectivity.core)
            implementation(libs.bundles.kotlinInjectAnvil)
        }
        val mobileMain by creating {
            dependsOn(commonMain.get())
            androidMain.get().dependsOn(this)
            iosMain.get().dependsOn(this)
            dependencies {
                api(libs.connectivity.device)
            }
        }
        val nonMobileMain by creating {
            dependsOn(commonMain.get())
            jvmMain.get().dependsOn(this)
            wasmJsMain.get().dependsOn(this)
            dependencies {
                api(libs.connectivity.http)
            }
        }
    }
}