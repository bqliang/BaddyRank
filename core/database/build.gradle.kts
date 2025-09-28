plugins {
    alias(libs.plugins.baddyrank.android.library)
    alias(libs.plugins.baddyrank.hilt)
    alias(libs.plugins.baddyrank.android.room)
}

android {
    namespace = "com.bqliang.baddyrank.core.database"
}

dependencies {
    api(projects.core.model)
}
