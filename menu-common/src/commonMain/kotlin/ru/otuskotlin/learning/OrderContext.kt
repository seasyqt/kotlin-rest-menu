package ru.otuskotlin.learning

import kotlinx.datetime.Instant
import ru.otuskotlin.learning.models.*
import ru.otuskotlin.learning.models.order.Order
import ru.otuskotlin.learning.models.order.OrderCommand
import ru.otuskotlin.learning.models.order.OrderFilter
import ru.otuskotlin.learning.stubs.OrderStub
import ru.otuskotlin.learning.utils.NONE

data class OrderContext(
    var command: OrderCommand = OrderCommand.NONE,
    var state: State = State.NONE,
    var error: MutableList<CommonError> = mutableListOf(),

    var debugMode: DebugMode = DebugMode.PROD,
    var stub: OrderStub = OrderStub.NONE,

    var requestId: RequestId = RequestId.NONE,
    var startTime: Instant = Instant.NONE,

    var orderRequest: Order = Order(),
    var orderFilterRequest: OrderFilter = OrderFilter(),

    var orderResponse: Order = Order(),
    var orderListResponse: MutableList<Order> = mutableListOf()

)