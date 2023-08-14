package ru.otuskotlin.learning

import kotlinx.datetime.Instant
import ru.otuskotlin.learning.models.*
import ru.otuskotlin.learning.models.goods.Goods
import ru.otuskotlin.learning.models.goods.GoodsCommand
import ru.otuskotlin.learning.models.goods.GoodsFilter
import ru.otuskotlin.learning.stubs.GoodsStub
import ru.otuskotlin.learning.utils.NONE

data class GoodsContext(
    var command: GoodsCommand = GoodsCommand.NONE,
    var state: State = State.NONE,
    var errors: MutableList<CommonError> = mutableListOf(),

    var debugMode: DebugMode = DebugMode.PROD,
    var stub: GoodsStub = GoodsStub.NONE,

    var requestId: RequestId = RequestId.NONE,
    var startTime: Instant = Instant.NONE,

    var goodsRequest: Goods = Goods(),
    var goodsFilterRequest: GoodsFilter = GoodsFilter(),

    var goodsResponse: Goods = Goods(),
    var goodsListResponse: MutableList<Goods> = mutableListOf()
)