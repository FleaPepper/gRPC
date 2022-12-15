import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties

object Producer {

    private val properties = Properties()
    private var producer: KafkaProducer<Int, String>

    init {
        properties[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "127.0.0.1:9092"
        properties[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = IntegerSerializer().javaClass
        properties[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer().javaClass
        producer = KafkaProducer<Int, String>(properties)
    }

    fun sendMessage(message: String) {
        val record = ProducerRecord<Int, String>("demo-topic", message)
        producer.send(record)
        producer.flush()
        //producer.close()
    }

    fun flush() {
        producer.flush()
    }
}