rootProject.name = "quickMenu"

include("m1-l1-helloworld")
include("m2-menu")
include("m2-menu-api-v1-jackson")
include("m2-menu-api-v2-kmp")

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false
    }
}
