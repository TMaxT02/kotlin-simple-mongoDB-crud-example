plugins {
    kotlin("jvm") version "2.0.0"
}

group = "mc.plugin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("junit:junit:4.13.2")
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.10.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
tasks.test {
    useJUnitPlatform()
}