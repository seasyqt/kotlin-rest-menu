@JvmInline
value class GoodsOrderId(private val id: String){
    fun asString() = id

    companion object {
        val NONE = GoodsOrderId("")
    }

}