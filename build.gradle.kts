@file:Suppress("HardCodedStringLiteral")

import org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

plugins {
    id("java")
    jacoco
    id("org.owasp.dependencycheck") version "8.4.0"
}

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.mockito/mockito-core
    testImplementation("org.mockito:mockito-core:5.6.0")

    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    // Temporarily Wrapped Dependency
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks.check {
    dependsOn(":dependencyCheckAnalyze")
}

configure<DependencyCheckExtension> {
    format = org.owasp.dependencycheck.reporting.ReportGenerator.Format.ALL.toString()
    analyzers.knownExploitedURL = "https://raw.githubusercontent.com/TriNext/cisa-known-exploited-mirror/main/known_exploited_vulnerabilities.json"
    failBuildOnCVSS = 10.0F
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}