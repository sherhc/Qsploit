package animus.sherhc.webSocket

import io.ktor.websocket.*

class WebSocketServer(listener: WebSocketListener<WebSocketServerSession>) :
	ServerImpl<WebSocketServerSession>() {
	private val server = KtorServer(listener)

	override fun getRemoteIp(session: WebSocketServerSession) =
		server.getRemoteIp(session)

	override fun start() {
		server.start()
	}

	override fun close(session: WebSocketServerSession, code: Int, reason: String) {
		server.close(session, code, reason)
	}

	override suspend fun send(text: String) {
		server.send(text)
	}

	override suspend fun send(byte: ByteArray) {
		server.send(byte)
	}
}