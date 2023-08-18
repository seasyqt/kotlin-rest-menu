package models.order

import models.goods.GoodsId
import java.math.BigInteger

data class GoodsOrder(
    var id: GoodsId = GoodsId.NONE,
    var name: String = "",
    var price: BigInteger = BigInteger.ZERO,
    var count: BigInteger = BigInteger.ZERO
)
