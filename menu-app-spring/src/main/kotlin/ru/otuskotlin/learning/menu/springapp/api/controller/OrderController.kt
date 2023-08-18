package ru.otuskotlin.learning.menu.springapp.api.controller

import org.springframework.web.bind.annotation.*
import ru.otuskotlin.learning.api.v1.models.*
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.menu.mappers.*
import ru.otuskotlin.learning.menu.springapp.service.OrderBlockingProcessor


@RestController
@RequestMapping("v1/order")
class OrderController(private val processor: OrderBlockingProcessor) {

    @PostMapping("create")
    fun createAd(@RequestBody request: OrderCreateRequestDto): OrderCreateResponseDto {
        val context = OrderContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportCreate()
    }

    @PostMapping("read")
    fun readAd(@RequestBody request: OrderReadRequestDto): OrderReadResponseDto {
        val context = OrderContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportRead()
    }

    @PostMapping("update")
    fun updateAd(@RequestBody request: OrderUpdateRequestDto): OrderUpdateResponseDto {
        val context = OrderContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportUpdate()
    }

    @PostMapping("delete")
    fun deleteAd(@RequestBody request: OrderDeleteRequestDto): OrderDeleteResponseDto {
        val context = OrderContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportDelete()
    }

    @PostMapping("search")
    fun searchAd(@RequestBody request: OrderSearchRequestDto): OrderSearchResponseDto {
        val context = OrderContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportSearch()
    }
}