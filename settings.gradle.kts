rootProject.name = "quickMenu"

include("m1-l1-helloworld")
include("menu-api-v1-jackson")
include("menu-api-v2-kmp")
include("menu-common")
include("menu-mappers-v1")

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false
    }
}

