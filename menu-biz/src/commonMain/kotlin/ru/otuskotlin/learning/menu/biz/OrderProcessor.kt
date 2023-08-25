package ru.otuskotlin.learning.menu.biz

import ru.otuskotlin.learning.menu.common.OrderContext
import ru.otuskotlin.learning.stub.OrderStubObject


class OrderProcessor {
    
    suspend fun exec(context: OrderContext) {
        context.orderResponse = OrderStubObject.get()
    }
}