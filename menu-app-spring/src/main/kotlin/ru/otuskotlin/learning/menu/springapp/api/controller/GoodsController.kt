package ru.otuskotlin.learning.menu.springapp.api.controller

import models.goods.GoodsCommand
import org.springframework.web.bind.annotation.*
import ru.otuskotlin.learning.api.v1.models.*
import ru.otuskotlin.learning.menu.biz.GoodsProcessor
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.menu.mappers.*


@RestController
@RequestMapping("v1/goods")
class GoodsController(private val processor: GoodsProcessor) {

    @PostMapping("create")
    suspend fun createGoods(@RequestBody request: GoodsCreateRequestDto): GoodsCreateResponseDto =
        process(processor, GoodsCommand.CREATE, request)


    @PostMapping("read")
    suspend fun readGoods(@RequestBody request: GoodsReadRequestDto): GoodsReadResponseDto =
        process(processor, GoodsCommand.READ, request)


    @PostMapping("update")
    suspend fun updateGoods(@RequestBody request: GoodsUpdateRequestDto): GoodsUpdateResponseDto =
        process(processor, GoodsCommand.UPDATE, request)


    @PostMapping("delete")
   suspend fun deleteGoods(@RequestBody request: GoodsDeleteRequestDto): GoodsDeleteResponseDto =
        process(processor, GoodsCommand.DELETE, request)


    @PostMapping("search")
    suspend fun searchGoods(@RequestBody request: GoodsSearchRequestDto): GoodsSearchResponseDto =
        process(processor, GoodsCommand.SEARCH, request)

}