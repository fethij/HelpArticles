import com.tewelde.articles.addKspDependencyForAllTargets

plugins {
    alias(libs.plugins.articles.kotlinMultiplatform)
    alias(libs.plugins.articles.featureMultiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.room)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.model)
        }
        val nonWebMain by creating {
            dependsOn(commonMain.get())
            androidMain.get().dependsOn(this)
            iosMain.get().dependsOn(this)
            jvmMain.get().dependsOn(this)
            dependencies {
                api(libs.room.runtime)
                implementation(libs.androidx.sqlite.bundled)
            }
        }
        webMain.dependencies {
            implementation(libs.kstore)
            implementation(libs.kstore.storage)
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

addKspDependencyForAllTargets(libs.room.compiler)