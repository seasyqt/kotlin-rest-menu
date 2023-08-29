package ru.otuskotlin.learning.menu.repo.sql

open class SqlProperties(
    val url: String = "jdbc:postgresql://localhost:5432/menu",
    val user: String = "postgres",
    val password: String = "menu-pass",
    val schema: String = "menu",
    val dropDatabase: Boolean = false

)
