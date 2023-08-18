package models.order
@JvmInline
value class BuyerId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = BuyerId("")
    }

}