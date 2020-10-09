plugins {
    java
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.postgresql:postgresql:42.2.16")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

application {
    mainClass.set("com.alex.chatserver.AppInitializer")
}