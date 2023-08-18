import models.goods.Goods
import models.goods.GoodsId
import models.goods.GoodsType
import java.math.BigInteger
object GoodsStubPizza {

    val GOODS_PIZZA: Goods
        get() = Goods(
            id = GoodsId("123"),
            name = "Пицца Пеперони",
            type = GoodsType.PIZZA,
            price = BigInteger.valueOf(100),
            weight = "100 гр"
        )

    val GOODS_SNACK = GOODS_PIZZA.copy(type = GoodsType.SNACK)
}