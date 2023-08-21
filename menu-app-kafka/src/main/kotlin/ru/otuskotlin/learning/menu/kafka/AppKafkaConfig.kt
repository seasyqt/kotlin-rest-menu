package ru.otuskotlin.learning.menu.kafka

class AppKafkaConfig(
    val kafkaHosts: List<String> = KAFKA_HOSTS,
    val kafkaGroupId: String = KAFKA_GROUP_ID,
    val kafkaGoodsTopicIn: String = KAFKA_GOODS_TOPIC_IN,
    val kafkaGoodsTopicOut: String = KAFKA_GOODS_TOPIC_OUT,
) {
    companion object {
        private const val KAFKA_HOST_VAR = "KAFKA_HOSTS"
        private const val KAFKA_GOODS_TOPIC_IN_VAR = "KAFKA_GOODS_TOPIC_IN"
        private const val KAFKA_GOODS_TOPIC_OUT_VAR = "KAFKA_GOODS_TOPIC_OUT"
        private const val KAFKA_GROUP_ID_VAR = "KAFKA_GROUP_ID"

        val KAFKA_HOSTS by lazy { (System.getenv(KAFKA_HOST_VAR) ?: "").split("\\s*[,;]\\s*") }
        val KAFKA_GROUP_ID by lazy { System.getenv(KAFKA_GROUP_ID_VAR) ?: "quick-menu" }
        val KAFKA_GOODS_TOPIC_IN by lazy { System.getenv(KAFKA_GOODS_TOPIC_IN_VAR) ?: "menu-goods-in" }
        val KAFKA_GOODS_TOPIC_OUT by lazy { System.getenv(KAFKA_GOODS_TOPIC_OUT_VAR) ?: "menu-goods-out" }
    }
}