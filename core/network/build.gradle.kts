plugins {
    alias(libs.plugins.baddyrank.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.baddyrank.hilt)
}

android {
    buildFeatures {
        buildConfig = true
    }

    namespace = "com.bqliang.baddyrank.core.network"
}

dependencies {
    api(projects.core.model)
    implementation(projects.core.network.data)

    // Ktor
    implementation(ktorLibs.client.core)
    implementation(ktorLibs.client.contentNegotiation)
    implementation(ktorLibs.client.okhttp)
    implementation(ktorLibs.client.logging)
    implementation(ktorLibs.client.serialization)
    implementation(ktorLibs.serialization.kotlinx.json)
}