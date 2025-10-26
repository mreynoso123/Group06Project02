plugins {
  id("java-library")
  alias(libs.plugins.jetbrains.kotlin.jvm)
  id("org.springframework.boot") version "3.5.7"
  id("io.spring.dependency-management") version "1.1.7"
  id("org.openapi.generator") version "7.6.0"
}

val openApiSpec = layout.projectDirectory.file("openapi.json")
java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
    vendor = JvmVendorSpec.ADOPTIUM
  }
}

dependencies {
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
  implementation("org.springframework.boot:spring-boot-starter-web")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict")
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.named("compileJava") {
  dependsOn(tasks.openApiGenerate)
}

openApiGenerate {
  generatorName.set("kotlin")
  library.set("jvm-retrofit2")
  inputSpec.set(openApiSpec.asFile.absolutePath)
  // outputDir.set("${layout.buildDirectory}/generated/openapi-kotlin-client")
  outputDir.set(layout.buildDirectory.dir("generated/openapi-client2").get().asFile.absolutePath)
  apiPackage.set("example.client.api")
  modelPackage.set("example.client.model")
  invokerPackage.set("example.client")
  configOptions.set(
    mapOf(
      "dateLibrary" to "java8",          // java.time types
      "library" to "jvm-retrofit2",      // or "jvm-okhttp3", "multiplatform"
      "serializationLibrary" to "jackson",// or "kotlinx_serialization"
    ),
  )
}
