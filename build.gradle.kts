import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

allprojects{
    group = "ru.otuskotlin.learning"
    version = "1.0"

    repositories {
        google()
        mavenCentral()
    }
}

subprojects{
    tasks.withType<KotlinCompile>{
        kotlinOptions.jvmTarget = "17"
    }
}
