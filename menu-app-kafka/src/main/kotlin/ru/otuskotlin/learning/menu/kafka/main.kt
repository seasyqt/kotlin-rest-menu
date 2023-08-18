package ru.otuskotlin.learning.menu.kafka
fun main() {
    val config = AppKafkaConfig()
    val consumer = AppKafkaConsumer(config, listOf(ConsumerStrategyJackson()))
    consumer.run()
}