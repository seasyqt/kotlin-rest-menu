package ru.otus.otuskotlin.marketplace.biz.validation

import GoodsRepoStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.goods.GoodsCommand
import ru.otuskotlin.learning.menu.biz.GoodsProcessor
import ru.otuskotlin.learning.menu.common.GoodsCorSettings
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationUpdateTest {

    private val command = GoodsCommand.UPDATE
    private val processor = GoodsProcessor(GoodsCorSettings(repoTest = GoodsRepoStub()))


    @Test fun correctName() = validationNameCorrect(command, processor)
    @Test fun trimName() = validationNameTrim(command, processor)
    @Test fun emptyName() = validationNameEmpty(command, processor)
    @Test fun badSymbolsName() = validationNameSymbols(command, processor)

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)


}

