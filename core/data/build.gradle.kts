plugins {
    alias(libs.plugins.baddyrank.android.library)
}

android {
    namespace = "com.bqliang.baddyrank.core.data"
}

dependencies {
    api(projects.core.network)
    api(projects.core.database)
}