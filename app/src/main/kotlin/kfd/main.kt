package kfd

interface MessageSender {
    fun send(message: String)
}

class ConsoleSender : MessageSender {
    override fun send(message: String) {
        println(message)
    }
}

class MemorySender: MessageSender {
    private val messages = mutableListOf<String>()

    override fun send(message: String) {
        messages.add(message)
        println("MemSender: ${messages}")
    }
}

class OrderService (
    private val sender: MessageSender
) {

    fun notifyReady(orderID: String) {
        val message = "Order $orderID is ready"
        sender.send(message)
    }
}

fun main() {
    val orderService = OrderService(sender = ConsoleSender())
    orderService.notifyReady("A-2")

    val orderServiceMem = OrderService(sender = MemorySender())
    orderServiceMem.notifyReady("A-3")
}