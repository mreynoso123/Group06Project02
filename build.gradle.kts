// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.spotless)
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
    target("**/*.kt", "**/*.kts")
    ktlint()
    trimTrailingWhitespace()
    endWithNewline()
  }

  format("misc") {
    target("*.gradle", ".gitattributes", ".gitignore", ".editorconfig", "*.properties")

    trimTrailingWhitespace()
    endWithNewline()
  }
}
