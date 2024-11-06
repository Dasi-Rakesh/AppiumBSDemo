plugins {
    kotlin("jvm") version "2.0.0"
}

group = "com.khealth.automation.tests"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.appium:java-client:9.2.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


fun parseTags(tagString: String?): List<String> {
    return tagString?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() } ?: listOf()
}

tasks.register<Test>("testWithTags") {
    group = "verification"
    description = "Run tests with specified tags and environment"
    useJUnitPlatform {
        val tags = parseTags(project.findProperty("tags") as? String)
        if (tags.isNotEmpty()) {
            includeTags(*tags.toTypedArray())
        }
    }
}
