package ru.otuskotlin.learning.menu.biz

import OrderStub
import ru.otuskotlin.learning.menu.common.OrderContext


class OrderProcessor {
    suspend fun exec(context: OrderContext) {
        context.orderResponse = OrderStub.get()
    }
}