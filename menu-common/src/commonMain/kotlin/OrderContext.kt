package ru.otuskotlin.learning.menu.common

import kotlinx.datetime.Instant
import models.CommonError
import models.DebugMode
import models.RequestId
import models.State
import models.order.Order
import models.order.OrderCommand
import models.order.OrderFilter
import stubs.OrderStub
import utils.NONE

data class OrderContext(
    var command: OrderCommand = OrderCommand.NONE,
    var state: State = State.NONE,
    var errors: MutableList<CommonError> = mutableListOf(),

    var debugMode: DebugMode = DebugMode.PROD,
    var stub: OrderStub = OrderStub.NONE,

    var requestId: RequestId = RequestId.NONE,
    var startTime: Instant = Instant.NONE,

    var orderRequest: Order = Order(),
    var orderFilterRequest: OrderFilter = OrderFilter(),

    var orderResponse: Order = Order(),
    var orderListResponse: MutableList<Order> = mutableListOf()

)