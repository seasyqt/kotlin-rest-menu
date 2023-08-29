package ru.otus.otuskotlin.marketplace.biz.validation

import GoodsRepoStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.goods.GoodsCommand
import ru.otuskotlin.learning.menu.biz.GoodsProcessor
import ru.otuskotlin.learning.menu.common.GoodsCorSettings
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationReadTest {

    private val command = GoodsCommand.READ
    private val processor = GoodsProcessor(GoodsCorSettings(repoTest = GoodsRepoStub()))

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)

}

