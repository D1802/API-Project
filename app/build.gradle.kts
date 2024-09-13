/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.4/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use TestNG framework, also requires calling test.useTestNG() below
    testImplementation("org.testng:testng:7.5.1")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:32.1.1-jre")

    // https://mvnrepository.com/artifact/org.apache.poi/poi
    implementation("org.apache.poi:poi:5.2.2")

    // https://mvnrepository.com/artifact/io.rest-assured/rest-assured
    testImplementation("io.rest-assured:rest-assured:5.2.0")

    // https://mvnrepository.com/artifact/io.rest-assured/rest-assured
    testImplementation("io.rest-assured:rest-assured:4.3.3")
    
    // https://mvnrepository.com/artifact/org.json/json
    implementation("org.json:json:20171018")

    //implementation("org.everit.json:org.everit.json.schema:1.14.0")


    // https://mvnrepository.com/artifact/io.rest-assured/json-schema-validator
    implementation("io.rest-assured:json-schema-validator:4.3.0")





}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

application {
    // Define the main class for the application.
    mainClass.set("api.project.App")
}

tasks.named<Test>("test") {
    // Use TestNG for unit tests.
    useTestNG()
}
