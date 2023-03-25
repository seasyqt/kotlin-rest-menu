rootProject.name = "otus-kotlin"
include("m1-l1-helloworld")

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion apply false
    }
}
