plugins {
    id("java")
}

group = "ru.bagalast"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "ru.bagalast.Main" // замените на ваш главный класс
    }
}
