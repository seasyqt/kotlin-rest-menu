rootProject.name = "otus-kotlin"
include("m1-l1-helloworld")
include("m2-menu")

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion apply false
    }
}
