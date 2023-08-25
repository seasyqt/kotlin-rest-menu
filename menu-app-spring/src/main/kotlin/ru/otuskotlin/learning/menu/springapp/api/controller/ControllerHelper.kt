package ru.otuskotlin.learning.menu.springapp.api.controller

import models.goods.GoodsCommand
import ru.otuskotlin.learning.api.v1.models.IRequestDto
import ru.otuskotlin.learning.api.v1.models.IResponseDto
import ru.otuskotlin.learning.menu.biz.GoodsProcessor
import ru.otuskotlin.learning.menu.mappers.fromTransport
import ru.otuskotlin.learning.menu.mappers.toTransport

suspend inline fun <reified Q : IRequestDto, reified R : IResponseDto> process(
    processor: GoodsProcessor,
    command: GoodsCommand,
    request: Q
): R  = processor.process(command,
        { ctx -> ctx.fromTransport(request) },
        { ctx -> ctx.toTransport() as R })
