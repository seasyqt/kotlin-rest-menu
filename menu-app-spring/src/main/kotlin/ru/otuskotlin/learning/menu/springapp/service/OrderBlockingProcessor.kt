package ru.otuskotlin.learning.menu.springapp.service

import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import ru.otuskotlin.learning.menu.biz.OrderProcessor
import ru.otuskotlin.learning.menu.common.OrderContext

@Service
class OrderBlockingProcessor {
    private val processor = OrderProcessor()

    fun exec(context: OrderContext) = runBlocking { processor.exec(context) }
}