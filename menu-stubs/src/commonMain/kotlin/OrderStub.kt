import OrderStubPizza.ORDER_SALAD
import models.order.Order

object OrderStub {
    fun get(): Order = ORDER_SALAD.copy()
}