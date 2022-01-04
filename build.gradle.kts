plugins {
    kotlin("jvm") version "1.6.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("org.springframework:spring-context:5.3.14")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    compileOnly("org.projectlombok:lombok:1.18.14")
    annotationProcessor("org.projectlombok:lombok:1.18.14")

    testCompileOnly("org.projectlombok:lombok:1.18.14")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.14")
}

application {
    mainClass.set("com.alex.chat.server.AppInitializer")
}

tasks.test {
    useJUnitPlatform()
}
