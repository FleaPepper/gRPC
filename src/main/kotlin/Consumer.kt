import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.IntegerDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*

object Consumer {

    private val properties = Properties()
    private var consumer : KafkaConsumer<Int, String>

    init {
        properties[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "127.0.0.1:9092"
        properties[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = IntegerDeserializer().javaClass
        properties[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer().javaClass
        properties[ConsumerConfig.GROUP_ID_CONFIG] = "grpc-kafka-test"
        properties[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "latest"
        //properties[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = "false"
        consumer = KafkaConsumer<Int, String>(properties)
        //consumer.subscribe(Collections.singleton("demo-topic"))
        val topicPartition = TopicPartition("demo-topic", 0)
        val partitions = listOf(topicPartition)
        consumer.assign(partitions)
        consumer.seekToEnd(partitions)
    }

    fun getMessages(): ConsumerRecords<Int, String>? {
        return consumer.poll(Duration.ofMillis(100))
    }
}