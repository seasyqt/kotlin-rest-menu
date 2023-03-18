import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

allprojects{
    repositories {
        google()
        mavenCentral()
    }
}

subprojects{
    group = "org.example"
    version = "1.0-SNAPSHOT"
    tasks.withType<KotlinCompile>{
        kotlinOptions.jvmTarget = "11"
    }
}
