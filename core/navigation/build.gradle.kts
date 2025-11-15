plugins {
    alias(libs.plugins.articles.kotlinMultiplatform)
    alias(libs.plugins.articles.featureMultiplatform)
    alias(libs.plugins.articles.composeMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.common)
//            implementation(projects.core.model)

//            api(libs.bundles.circuit)
        }
    }
}