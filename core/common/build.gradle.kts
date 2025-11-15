plugins {
    alias(libs.plugins.articles.kotlinMultiplatform)
    alias(libs.plugins.articles.composeMultiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.model)

            api(compose.components.resources)
            api(libs.kermit)

            implementation(libs.bundles.kotlinInjectAnvil)
            implementation(libs.bundles.circuit)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.tewelde.articles.resources"
    generateResClass = always
}

dependencies {
    ksp(libs.kotlinInject.compiler)
    ksp(libs.kotlinInject.anvil.compiler)
}