package ru.otuskotlin.learning.menu.springapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.otuskotlin.learning.menu.biz.GoodsProcessor

@Configuration
class CorConfig {

    @Bean
    fun processor() = GoodsProcessor()
}