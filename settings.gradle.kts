pluginManagement {
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

plugins {
    /**
     * v1.0.0 requires Java 17 and is compatible with Gradle 9. v0.10.0 is last version before v1.0.0.
     */
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}

dependencyResolutionManagement {
    /**
     * Specify the places to look when searching for dependencies.
     * First, search under the Google Maven repository. Dependency URLs look like https://dl.google.com/dl/android/maven20/.
     * Then, search under the Maven central repository. Dependency URLs look like https://repo.maven.apache.org/maven2/.
     */
    repositories {
        google()
        mavenCentral()
    }

    /**
     * Ensures that this file (`settings.gradle.kts`) is the only place that that can specify the
     * remote repositories to look for when searching for dependencies (the `repositories {}` block
     * above). If this is attempted in another file (say, a `build.gradle.kts` file), it will
     * cause the build to fail.
     */
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

rootProject.name = "Group06Project02"
include(":app")
 