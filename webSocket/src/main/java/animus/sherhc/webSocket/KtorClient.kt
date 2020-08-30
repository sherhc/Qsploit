package animus.sherhc.webSocket

import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import io.ktor.util.*

class KtorClient {
	@KtorExperimentalAPI
	private val client = HttpClient {
		install(WebSockets)
	}

	@KtorExperimentalAPI
	suspend fun a() {
		client.ws(
			port = 8080, path = "/"
		) { // this: DefaultClientWebSocketSession

			// Send text frame.
			send("Hello, Text frame")

			// Send text frame.
			send(Frame.Text("Hello World"))

			// Send binary frame.


			// Receive frame.
			when (val frame = incoming.receive()) {
				is Frame.Text -> println(frame.readText())
				is Frame.Binary -> println(frame.readBytes())
			}
		}
	}
}