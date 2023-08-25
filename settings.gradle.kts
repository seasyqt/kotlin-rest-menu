rootProject.name = "quickMenu"

include("m1-l1-helloworld")
include("menu-api-v1-jackson")
include("menu-api-v2-kmp")
include("menu-common")
include("menu-mappers-v1")
include("menu-app-spring")
include("menu-biz")
include("menu-stubs")
include("menu-app-kafka")
include("menu-lib-cor")

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val springframeworkBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val pluginSpringVersion: String by settings
    val bmuschkoVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false

        id("org.springframework.boot") version springframeworkBootVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
        id("io.spring.dependency-management") version springDependencyManagementVersion apply false
        kotlin("plugin.spring") version pluginSpringVersion apply false

    }
}
