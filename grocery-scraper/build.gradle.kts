plugins {
    id("java")
}

group = "com.aleddineabsi.scrapper"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.jsoup:jsoup:1.16.1")
    implementation("org.seleniumhq.selenium:selenium-java:4.21.0")
}

tasks.test {
    useJUnitPlatform()
}