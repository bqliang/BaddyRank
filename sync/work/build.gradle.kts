plugins {
    alias(libs.plugins.baddyrank.android.library)
    alias(libs.plugins.baddyrank.hilt)
}

android {
    namespace = "com.bqliang.baddyrank.sync"
}

dependencies {
    implementation(projects.core.data)
    ksp(libs.hilt.ext.compiler)
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.ext.work)
}