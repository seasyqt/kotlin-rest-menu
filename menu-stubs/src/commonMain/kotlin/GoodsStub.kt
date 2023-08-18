import GoodsStubPizza.GOODS_PIZZA
import models.goods.Goods

object GoodsStub {
    fun get(): Goods = GOODS_PIZZA.copy()
}