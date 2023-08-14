plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":menu-api-v1-jackson"))
    implementation(project(":menu-common"))

    testImplementation(kotlin("test-junit"))
}