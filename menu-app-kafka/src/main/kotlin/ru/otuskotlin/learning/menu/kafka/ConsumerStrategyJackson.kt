package ru.otuskotlin.learning.menu.kafka


import ru.otuskotlin.learning.api.v1.*
import ru.otuskotlin.learning.api.v1.models.IRequestDto
import ru.otuskotlin.learning.api.v1.models.IResponseDto
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.mappers.fromTransport
import ru.otuskotlin.learning.menu.mappers.toTransport


interface ConsumerStrategy {
    fun topics(config: AppKafkaConfig): InputOutputTopics
    fun serialize(source: GoodsContext): String
    fun deserialize(value: String, target: GoodsContext)
}
class ConsumerStrategyJackson : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaGoodsTopicIn, config.kafkaGoodsTopicOut)
    }

    override fun serialize(source: GoodsContext): String {
        val response: IResponseDto = source.toTransport()
        return apiJacksonResponseSerialize(response)
    }

    override fun deserialize(value: String, target: GoodsContext) {
        val request: IRequestDto = apiJacksonRequestDeserialize(value)
        target.fromTransport(request)
    }
}


