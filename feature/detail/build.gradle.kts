plugins {
    alias(libs.plugins.articles.kotlinMultiplatform)
    alias(libs.plugins.articles.composeMultiplatform)
    alias(libs.plugins.articles.featureMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.common)
            implementation(projects.core.navigation)
            implementation(projects.core.model)
            implementation(projects.core.domain)

            implementation(libs.markdown)
            implementation(libs.markdown.m3)

            implementation(libs.bundles.circuit)
        }
    }
}