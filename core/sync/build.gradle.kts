plugins {
    alias(libs.plugins.articles.kotlinMultiplatform)
    alias(libs.plugins.articles.featureMultiplatform)
    alias(libs.plugins.articles.composeMultiplatform)
}

kotlin {
    sourceSets {

        sourceSets {
            commonMain.dependencies {
                implementation(projects.core.common)
                implementation(projects.core.database)
                implementation(projects.core.domain)
                implementation(projects.core.connectivity)
            }
            val nonWebMain by creating {
                dependsOn(commonMain.get())
                androidMain.get().dependsOn(this)
                iosMain.get().dependsOn(this)
                jvmMain.get().dependsOn(this)
                dependencies {
                    api(libs.meeseeks.runtime)
                }
            }
        }
    }
}
