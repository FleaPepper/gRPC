import com.example.grpc.TextServiceGrpc
import com.example.grpc.TextServiceProto
import com.example.grpc.TextServiceProto.AllMessages
import com.google.gson.Gson
import io.grpc.stub.StreamObserver

class TextServiceImpl : TextServiceGrpc.TextServiceImplBase() {

    private val consumer = Consumer
    private val producer = Producer
    private val gson = Gson()

    override fun sendText(
        request: TextServiceProto.TextMessage,
        responseObserver: StreamObserver<TextServiceProto.OKResponse>
    ) {
        val message = Message(request.text)
        val jsonMessage = gson.toJson(message)
        producer.sendMessage(jsonMessage)
        val response = TextServiceProto.OKResponse.newBuilder().setText("Message sent")
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun getText(
        request: TextServiceProto.GetTextRequest,
        responseObserver: StreamObserver<AllMessages>
    ) {
        val consumerRecords = consumer.getMessages()
        //val jsonList = ArrayList<Message>()
        val messages = ArrayList<String>()
        for (record in consumerRecords!!) {
            //jsonList.add(gson.fromJson(record.value(), Message().javaClass))
            messages.add(record.value())
        }
        val response = AllMessages.newBuilder().addAllMessage(messages).build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}