package ru.otuskotlin.learning.menu.springapp.service

import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import ru.otuskotlin.learning.menu.biz.GoodsProcessor
import ru.otuskotlin.learning.menu.common.GoodsContext

@Service
class GoodsBlockingProcessor {
    private val processor = GoodsProcessor()

    fun exec(context: GoodsContext) = runBlocking { processor.exec(context) }
}