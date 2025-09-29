plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.baddyrank.jvm.application)
}

application {
    mainClass.set("com.bqliang.baddy.crawler.MainKt")
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.network.data)

    // DeepSeek
    implementation(libs.aallam.openai.client) // also need one of Ktor's engines.
    // Json
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    // Ktor
    implementation(ktorLibs.client.core)
    implementation(ktorLibs.client.okhttp)
    implementation(ktorLibs.client.logging)
    // Jsoup
    implementation(libs.jsoup)
    // Koin
    implementation(libs.koin.core)
    // Log
    implementation(libs.kotlin.logging)
    implementation(libs.logback.classic)

    // Test
    testImplementation(libs.kotlin.test)
}