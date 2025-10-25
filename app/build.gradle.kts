/**
 * Gradle Versions: Gradle 7: Version Catalog as experimental, Toolchain repositories Gradle 8: Adds
 * Java Toolchain repositories Gradle 9: Configuration Cache recommended (like Build Cache), default
 * to Kotlin DSL, Kotlin 2.0 embeds Groovy 4.0. Requires Java 17+
 *
 * Gradle and Java Versions:
 * - To run Java 17, need Gradle 7.3
 * - To run Java 21, need Gradle 8.4
 * - To run Java 25, need Gradle 9.1.0
 * - To run Gradle on Java 21, need Gradle 8.5
 * - To run Gradle on Java 25, need Gradle 9.1.0
 * See compatibility matrix:
 *   https://docs.gradle.org/current/userguide/compatibility.html
 *
 * Notes:
 * - For greatest support, best to stick with Gradle at least Gradle 8.5
 */
plugins {
   /**
    * Specify the addition of the "android-application" plugin. The `alias()` uses the "Version
    * Catalog" feature, which allows us to specify dependencies using "property access", rather then
    * a string literal. This allows for showing better errors if a typo in a dependency is made. To
    * view the version catalog, see the file at `./gradle/libs.versions.toml`.
    */
   alias(libs.plugins.android.application)
   alias(libs.plugins.spotless)
}

/**
 * This declares a Java toolchain. Using Java toolchains allows Gradle to automatically download and
 * manage the required JDK version for your build. It ensures that the correct Java version is used
 * for both compilation and execution. See: https://docs.gradle.org/current/userguide/toolchains.html#sec:using-java-toolchains
 */
java {
   toolchain {
      /**
       * Set the toolchain version to Java 11.
       *
       * Some Gradle configurations have "sourceCompatibility" and "targetCompatibility" options. This
       * option is similar using "11" instead of "JavaVersion.VERSION_11". This is preferred way to
       * set the Java version, since the other two options are deprecated and not recommended. See:
       * https://developer.android.com/build/jdks See:
       * https://docs.gradle.org/current/userguide/toolchains.html#sec:source-target-toolchain
       */
      languageVersion = JavaLanguageVersion.of(11)

      /**
       * By default, Android Studio use the JetBrains Runtime (JBR). It has specific "optimizations"
       * for Android Studio. Explicitly set the JVM vendor to JetBrains because when building the
       * Android project outside of Android Studio (i.e. an external terminal), the runtime may
       * differ. So by setting it explicitly, we avoid some situations where there are inconsistencies
       * across builds. See: https://developer.android.com/build/jdks
       *
       * Also, when Gradle chooses between JVM vendors, it prefers some over others. So by setting
       * "JetBrains" it avoids other cases of inconsistencies. See:
       * https://docs.gradle.org/current/userguide/toolchains.html#sec:precedence
       */
      vendor = JvmVendorSpec.JETBRAINS
   }
}

android {
   namespace = "com.mareyn.group06project02"
   compileSdk = 36

   defaultConfig {
      applicationId = "com.mareyn.group06project02"
      minSdk = 28
      targetSdk = 36
      versionCode = 1
      versionName = "1.0"

      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
   }

   buildTypes {
      release {
         isMinifyEnabled = false
         proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      }
   }
}

dependencies {
   implementation(libs.appcompat)
   implementation(libs.material)
   implementation(libs.activity)
   implementation(libs.constraintlayout)
   testImplementation(libs.junit)
   androidTestImplementation(libs.ext.junit)
   androidTestImplementation(libs.espresso.core)
}

spotless {
   java {
      target("**/*.java")
      importOrder()
      trimTrailingWhitespace()
      endWithNewline()
      googleJavaFormat()
      formatAnnotations()
   }

   kotlin {
      target("**/*.kt", "**/*.kts", "build.gradle.kts")
      ktlint()
      trimTrailingWhitespace()
      endWithNewline()
   }

   format("misc") {
      target("*.gradle", ".gitattributes", ".gitignore", ".editorconfig")

      trimTrailingWhitespace()
      endWithNewline()
   }
}
