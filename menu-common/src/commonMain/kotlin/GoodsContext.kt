package ru.otuskotlin.learning.menu.common

import kotlinx.datetime.Instant
import models.CommonError
import models.DebugMode
import models.RequestId
import models.State
import models.goods.Goods
import models.goods.GoodsCommand
import models.goods.GoodsFilter
import ru.otuskotlin.learning.menu.common.repo.IGoodsRepository
import stubs.GoodsStub
import utils.NONE

data class GoodsContext(
    var command: GoodsCommand = GoodsCommand.NONE,
    var state: State = State.NONE,
    var errors: MutableList<CommonError> = mutableListOf(),
    var settings: GoodsCorSettings = GoodsCorSettings.NONE,

    var debugMode: DebugMode = DebugMode.PROD,
    var stub: GoodsStub = GoodsStub.NONE,

    var requestId: RequestId = RequestId.NONE,
    var startTime: Instant = Instant.NONE,

    var goodsRequest: Goods = Goods(),
    var goodsFilterRequest: GoodsFilter = GoodsFilter(),

    var goodsValidating: Goods = Goods(),
    var goodsFilterValidating: GoodsFilter = GoodsFilter(),

    var goodsValidated: Goods = Goods(),
    var goodsFilterValidated: GoodsFilter = GoodsFilter(),

    var goodsResponse: Goods = Goods(),
    var goodsListResponse: MutableList<Goods> = mutableListOf(),


    var goodsRepo: IGoodsRepository = IGoodsRepository.NONE,
    var goodsRepoPrepare: Goods = Goods(),
    var goodsRepoRead: Goods = Goods(),
    var goodsRepoDone: Goods = Goods(),
    var goodsListRepoDone: MutableList<Goods> = mutableListOf(),
)