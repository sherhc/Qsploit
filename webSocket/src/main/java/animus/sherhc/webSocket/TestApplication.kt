package animus.sherhc.webSocket

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import org.slf4j.event.Level
import java.time.Duration
import java.util.*

fun Application.main() {
	install(CallLogging) {
		level = Level.ERROR
	}
	install(WebSockets) {
		pingPeriod = Duration.ofMinutes(1)
		timeout = Duration.ofSeconds(15)
		maxFrameSize =
			Long.MAX_VALUE // Disabled (max value). The connection will be closed if surpassed this length.
		masking = false
	}
	routing {
		webSocket("/") {
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
					}
				}
			} finally {

			}

		}
	}
}