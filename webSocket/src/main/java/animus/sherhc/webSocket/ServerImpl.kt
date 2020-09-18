package animus.sherhc.webSocket

abstract class ServerImpl<Session> {

	open fun getLocalIp() {}
	abstract fun getRemoteIp(session: Session): String
	abstract fun start()
	abstract suspend fun send(text: String)
	abstract suspend fun send(byte: ByteArray)
	open fun publish() {}
	abstract fun close(session: Session, code: Int, reason: String)

	/*abstract fun isClosed(session: Session)
	abstract fun isConnected(session: Session)*/
	var clientCount = 0
	var onClientClosed: (session: Session) -> Unit = {}
	var heartbeatInterval = 30
	var heartbeatType = 9
	open fun getUrl() {}

}