plugins {
    alias(libs.plugins.baddyrank.android.library)
    alias(libs.plugins.baddyrank.android.library.compose)
}

android {
    namespace = "com.bqliang.baddyrank.core.designsystem"
}

dependencies {
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.util)

    implementation(libs.coil.compose)
    implementation(libs.coil.ktor3)

    testImplementation(libs.bundles.androidx.compose.ui.test)
}
