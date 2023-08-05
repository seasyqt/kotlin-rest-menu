package ru.otuskotlin.learning.models.goods

@JvmInline
value class GoodsId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = GoodsId("")
    }

}
