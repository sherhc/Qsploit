package animus.sherhc.qsploit.post

import animus.sherhc.qsploit.SingletonHolder
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

class WebSocketManager(val _url: String) {
	companion object : SingletonHolder<WebSocketManager, String>(::WebSocketManager) {
		private val client = OkHttpClient.Builder()
			.connectTimeout(3, TimeUnit.SECONDS)
			.readTimeout(3, TimeUnit.SECONDS)
			.writeTimeout(3, TimeUnit.SECONDS)
			.build()
		const val CONNECTING = 0
		const val OPEN = 1
		const val CLOSING = 2
		const val CLOSED = 3

	}

	private val request = Request.Builder().url(_url).build()

	var readyState =
		CONNECTING
	var mWebSocket: WebSocket? = null

	private val _listener = object : WebSocketListener() {

		override fun onOpen(webSocket: WebSocket, response: Response) {
			mWebSocket = webSocket
			readyState =
				OPEN
		}

		override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
			readyState =
				CLOSING
		}

		override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
			readyState =
				CLOSED
		}


		override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
			readyState =
				CONNECTING
		}


	}

	suspend fun connect(listener: WebSocketListener) {
		mWebSocket = client.newWebSocket(
			request, listener
		)
	}

	fun close(code: Int, reason: String) {
		mWebSocket?.close(code, reason)
		mWebSocket = null
	}

	val isClosed: Boolean
		get() {
			if (this.readyState == 3) return true
			return this.mWebSocket == null
		}
	val isConnected: Boolean
		get() {
			return (this.readyState == 1) && (this.mWebSocket != null)
		}

	fun send(data: String) {
		mWebSocket?.send(data)
	}

	fun send(data: ByteString) {
		mWebSocket?.send(data)
	}
}