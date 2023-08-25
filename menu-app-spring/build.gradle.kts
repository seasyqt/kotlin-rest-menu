plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}



dependencies {
    val kotestVersion: String by project
    val springdocOpenapiUiVersion: String by project
    val coroutinesVersion: String by project
    val serializationVersion: String by project

    implementation("org.springframework.boot:spring-boot-starter-actuator") // info; refresh; springMvc output
    implementation("org.springframework.boot:spring-boot-starter-webflux") // Controller, Service, etc..
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocOpenapiUiVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin") // from models to json and Vice versa
    implementation("org.jetbrains.kotlin:kotlin-reflect") // for spring-boot app
    implementation("org.jetbrains.kotlin:kotlin-stdlib") // for spring-boot app
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

    // transport models
    implementation(project(":menu-common"))

    // v1 api
    implementation(project(":menu-api-v1-jackson"))
    implementation(project(":menu-mappers-v1"))

    // biz
    implementation(project(":menu-biz"))

    // tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux") // Controller, Service, etc..
    testImplementation("com.ninja-squad:springmockk:3.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1") // mockking beans
}

tasks {
    withType<ProcessResources> {
        from("$rootDir/specs") {
            into("/static")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}