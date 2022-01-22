plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-simple:1.7.33")
    implementation("org.springframework:spring-context:5.3.15")
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
}

application {
    mainClass.set("com.alex.chat.server.AppInitializer")
}

tasks.test {
    useJUnitPlatform()
}
