data class Message(val text: String = "default message") {
    override fun toString(): String {
        return text
    }
}