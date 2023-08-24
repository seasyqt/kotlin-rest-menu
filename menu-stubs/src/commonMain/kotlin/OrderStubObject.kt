package ru.otuskotlin.learning.stub

import models.order.Order
import ru.otuskotlin.learning.stub.OrderStubPizza.ORDER_SALAD

object OrderStubObject {
    fun get(): Order = ORDER_SALAD.copy()
}