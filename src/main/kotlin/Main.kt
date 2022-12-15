import io.grpc.ServerBuilder

fun main(args: Array<String>) {

    val server = ServerBuilder.forPort(8080).addService(TextServiceImpl()).build()

    server.start()
    println("Server started")

    server.awaitTermination()
}