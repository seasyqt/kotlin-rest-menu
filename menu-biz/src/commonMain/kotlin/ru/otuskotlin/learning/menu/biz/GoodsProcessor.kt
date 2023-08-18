package ru.otuskotlin.learning.menu.biz

import ru.otuskotlin.learning.menu.common.GoodsContext


class GoodsProcessor {
    suspend fun exec(context: GoodsContext) {
        context.goodsResponse = GoodsStub.get()
    }
}