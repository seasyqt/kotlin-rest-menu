package ru.otuskotlin.learning.menu.springapp.api.controller

import org.springframework.web.bind.annotation.*
import ru.otuskotlin.learning.api.v1.models.*
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.menu.mappers.*
import ru.otuskotlin.learning.menu.springapp.service.GoodsBlockingProcessor


@RestController
@RequestMapping("v1/goods")
class GoodsController(private val processor: GoodsBlockingProcessor) {

    @PostMapping("create")
    fun createGoods(@RequestBody request: GoodsCreateRequestDto): GoodsCreateResponseDto {
        val context = GoodsContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportCreate()
    }

    @PostMapping("read")
    fun readGoods(@RequestBody request: GoodsReadRequestDto): GoodsReadResponseDto {
        val context = GoodsContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportRead()
    }

    @PostMapping("update")
    fun updateGoods(@RequestBody request: GoodsUpdateRequestDto): GoodsUpdateResponseDto {
        val context = GoodsContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportUpdate()
    }

    @PostMapping("delete")
    fun deleteGoods(@RequestBody request: GoodsDeleteRequestDto): GoodsDeleteResponseDto {
        val context = GoodsContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportDelete()
    }

    @PostMapping("search")
    fun searchGoods(@RequestBody request: GoodsSearchRequestDto): GoodsSearchResponseDto {
        val context = GoodsContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportSearch()
    }
}