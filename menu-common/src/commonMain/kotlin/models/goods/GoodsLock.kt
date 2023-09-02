package ru.otuskotlin.learning.menu.common.models.goods

@JvmInline
value class GoodsLock(private val id: String) {
        fun asString() = id

        companion object {
            val NONE = GoodsLock("")
        }
}
