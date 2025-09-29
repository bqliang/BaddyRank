plugins {
    alias(libs.plugins.baddyrank.android.library)
    alias(libs.plugins.baddyrank.android.library.compose)
}

android {
    namespace = "com.bqliang.baddyrank.core.ui"
}

dependencies {
    api(projects.core.model)
    api(projects.core.designsystem)

    implementation(libs.coil.compose)
    implementation(libs.coil.ktor3)

    androidTestImplementation(libs.bundles.androidx.compose.ui.test)
}
