package ru.otuskotlin.learning.menu.springapp.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import ru.otuskotlin.learning.menu.repo.sql.SqlProperties

@ConfigurationProperties("sql")
class SqlPropertiesEx
    @ConstructorBinding
    constructor(
        url: String,
        user: String,
        password: String,
        schema: String,
        dropDatabase: Boolean
    ): SqlProperties(url, user, password, schema, dropDatabase)