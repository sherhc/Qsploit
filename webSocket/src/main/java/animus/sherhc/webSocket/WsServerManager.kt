package animus.sherhc.webSocket

class WsServerManager : IServer() {
	companion object {
		val manager: WsServerManager by lazy { WsServerManager() }
	}

	private val server = KtorServer()

	override fun connect() {
		server.connect()
	}

	override fun disconnect() {
		server.disconnect()
	}


	override var isConnected = server.isConnected
}