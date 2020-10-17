package animus.sherhc.webSocket

import io.ktor.application.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.websocket.*
import java.time.Duration
import java.util.*


fun Application.main() {
	TestApplication().apply { main() }
}

class TestApplication {
	fun Application.main() {
		install(WebSockets) {
			pingPeriod = Duration.ofMinutes(1)
			timeout = Duration.ofSeconds(15)
			maxFrameSize =
				Long.MAX_VALUE // Disabled (max value). The connection will be closed if surpassed this length.
			masking = false
		}
		routing {
			webSocket("/") {
				val session = call.sessions.get<ChatSession>()
				if (session == null) {
					close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
					return@webSocket
				}
				//memberJoin(session.id, this)
				try {
					for (frame in incoming) {
						when (frame) {
							is Frame.Text -> {
								val text = frame.readText()
								outgoing.send(Frame.Text("${Date(System.currentTimeMillis())}: $text"))

								if (text.equals("bye", ignoreCase = true)) {
									close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
								}
							}
							else -> {
							}
						}
					}
				} finally {
					//server.memberLeft(session.id, this)
				}

			}
		}
	}
}

data class ChatSession(val id: String)