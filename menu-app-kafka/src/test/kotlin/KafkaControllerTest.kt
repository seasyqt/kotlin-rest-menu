import org.apache.kafka.clients.consumer.*
import org.apache.kafka.clients.producer.*
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.*
import org.junit.Test
import ru.otuskotlin.learning.api.v1.*
import ru.otuskotlin.learning.api.v1.models.*
import ru.otuskotlin.learning.menu.kafka.*

import java.util.*
import kotlin.test.assertEquals

class KafkaControllerTest {
    @Test
    fun runKafka() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig()
        val inputTopic = config.kafkaGoodsTopicIn
        val outputTopic = config.kafkaGoodsTopicOut

        val app = AppKafkaConsumer(config, listOf(ConsumerStrategyJackson()), consumer = consumer, producer = producer)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "test-1",
                    apiJacksonRequestSerialize(
                        GoodsCreateRequestDto(
                            requestId = "11111111-1111-1111-1111-111111111111",
                            goods = GoodsCreateObjectDto(
                                name = "Pizza",
                                type = GoodsTypeDto.PIZZA,
                                price = "1000",
                                weight = "100гр"
                            ),
                            debug = GoodsDebugDto(
                                mode = RequestDebugModeDto.STUB,
                                stub = GoodsRequestDebugStubsDto.SUCCESS
                            )
                        )
                    )
                )
            )
            app.stop()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(inputTopic, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.run()

        val message = producer.history().first()
        val result = apiJacksonResponseDeserialize<GoodsCreateResponseDto>(message.value())
        assertEquals(outputTopic, message.topic())
        assertEquals("11111111-1111-1111-1111-111111111111", result.requestId)
        assertEquals("Пицца Пеперони", result.goods?.name)
    }

    companion object {
        const val PARTITION = 0
    }
}