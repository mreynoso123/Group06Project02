plugins {
  id("java-library")
  alias(libs.plugins.jetbrains.kotlin.jvm)
  alias(libs.plugins.springframework.boot)
  alias(libs.plugins.springDependencyManagement)
  alias(libs.plugins.springDocOpenApiGradlePlugin)
  alias(libs.plugins.openApiGenerator)
}

java {
  toolchain {
    // TODO: not working?:~/.local/share/gradle/jdks/eclipse_adoptium-21-amd64-linux.2/bin/java
    languageVersion = JavaLanguageVersion.of(21)
    vendor = JvmVendorSpec.ADOPTIUM
  }
}

dependencies {
  implementation(libs.springdoc.openapi.starter.webmvc.ui)
  developmentOnly(libs.spring.boot.devtools)
  implementation(libs.spring.boot.starter)
  implementation(libs.kotlin.reflect)
  testImplementation(libs.spring.boot.starter.test)
  testImplementation(libs.kotlin.test.junit5)
  testRuntimeOnly(libs.junit.platform.launcher)
  implementation(libs.spring.boot.starter.web)
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict")
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

val openApiSpec = layout.buildDirectory.file("openapi.json")
val retrofitClientDir = layout.projectDirectory.dir("../my-fork/retrofit-client")


tasks.register("buildAndUseClientFull") {
  dependsOn(tasks.openApiGenerate)
}

openApi {
  apiDocsUrl.set("http://localhost:8080/v3/api-docs")
  outputDir.set(openApiSpec.get().asFile.parentFile)
  outputFileName.set(openApiSpec.get().asFile.name)
  waitTimeInSeconds.set(10)
}

tasks.named("openApiGenerate") {
  dependsOn(tasks.generateOpenApiDocs)
}
openApiGenerate {
  inputSpec.set(openApiSpec.get().asFile.absolutePath)
  outputDir.set(retrofitClientDir.asFile.absolutePath)
  generatorName.set("kotlin")
  library.set("jvm-retrofit2")
  apiPackage.set("example.client.api")
  modelPackage.set("example.client.model")
  invokerPackage.set("example.client")
  groupId.set("example.openapitools")
  configOptions.set(
    mapOf(
      "dateLibrary" to "java8",
      "library" to "jvm-retrofit2",
      "serializationLibrary" to "jackson",
    ),
  )
}
