import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Tests {

    private var consumer = Consumer
    private val producer = Producer

    @BeforeEach
    fun init() {
        //Assumptions.assumeTrue(TextServiceImpl.isRunning)
        //var records = consumer.getMessages()
        consumer.getMessages()
        consumer.getMessages()
    }

    @Test
    fun messageIsDelivered() {
        producer.sendMessage("test message!")
        Thread.sleep(100)
        val records = consumer.getMessages()
        Assertions.assertTrue(records?.last()!!.value().equals("test message!"))
    }

//    @Test
//    fun getMessages() {
//        var records = consumer.getMessages()
//    }

    @Test
    fun messagesSaveOrder() {
        producer.sendMessage("first message")
        producer.flush()
        producer.sendMessage("second message")
        producer.flush()
        producer.sendMessage("third message")
        producer.flush()
        val records = consumer.getMessages()
        for (record in records!!)
            println(record.value())
        println(records?.count())
        Assertions.assertTrue(records?.elementAt(0)!!.value().equals("first message"))
        Assertions.assertTrue(records?.elementAt(1)!!.value().equals("second message"))
        Assertions.assertTrue(records?.elementAt(2)!!.value().equals("third message"))
    }
}