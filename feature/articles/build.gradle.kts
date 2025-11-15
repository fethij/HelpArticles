plugins {
    alias(libs.plugins.articles.kotlinMultiplatform)
    alias(libs.plugins.articles.composeMultiplatform)
    alias(libs.plugins.articles.featureMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.common)
            implementation(projects.core.connectivity)
            implementation(projects.core.domain)
            implementation(projects.core.model)
            implementation(projects.core.navigation)

            implementation(libs.composeIcons.tablerIcons)

            implementation(libs.bundles.circuit)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }

        jvmTest.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}