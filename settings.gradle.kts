pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("ktorLibs") {
            from("io.ktor:ktor-version-catalog:3.3.0")
        }
    }
}

rootProject.name = "BaddyRank"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":crawler")
include(":core:common")
include(":core:model")
include(":core:ui")
include(":core:network:data")
include(":core:data")
include(":core:network")
include(":core:database")
include(":sync:work")
include(":feature:ranking")
include(":core:designsystem")

check(JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_17)) {
    """
    BaddyRank requires JDK 17+ but it is currently using JDK ${JavaVersion.current()}.
    Java Home: [${System.getProperty("java.home")}]
    https://developer.android.com/build/jdks#jdk-config-in-studio
    """.trimIndent()
}
