plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("kapt")
}

val mapstructVersion = "1.5.3.Final"
val springdocVersion = "2.0.2"

dependencies {
    implementation(project(":shared"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    kapt("org.mapstruct:mapstruct-processor:$mapstructVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:$springdocVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    kaptTest("org.mapstruct:mapstruct-processor:$mapstructVersion")
}

kapt {
    arguments {
        arg("mapstruct.defaultComponentModel", "spring")
    }
}
