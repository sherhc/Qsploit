package animus.sherhc.webSocket

import java.net.InetAddress
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

open class IServer {
	open fun connect() {}
	open fun disconnect() {}
	open var isConnected: Boolean = false
	val clientCounter = AtomicInteger()
	val clients = ConcurrentHashMap<String, InetAddress>()
	val lastMessages = LinkedList<String>()
	fun memberJoin(guid: String, socket: InetAddress) {}

}