package models.goods

data class GoodsFilter(
    var nameSearch: String = "",
    var type: GoodsType = GoodsType.NONE
)
