package animus.sherhc.webSocket

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MyAppTest {
	@Test
	fun testRequests() = withTestApplication(Application::main) {
		with(handleRequest(HttpMethod.Get, "/")) {
			assertEquals(HttpStatusCode.OK, response.status())
			assertEquals("Hello from Ktor Testable sample application", response.content)
		}
		with(handleRequest(HttpMethod.Get, "/index.html")) {
			assertFalse(requestHandled)
		}
	}

	@Test
	fun testConversation() {
		withTestApplication {
			application.install(WebSockets)
			val received = arrayListOf<String>()
			application.routing {
				webSocket("/echo") {
					try {
						while (true) {
							val text = (incoming.receive() as Frame.Text).readText()
							received += text
							outgoing.send(Frame.Text(text))
						}
					} catch (e: ClosedReceiveChannelException) {
						// Do nothing!
					} catch (e: Throwable) {
						e.printStackTrace()
					}
				}
			}

			handleWebSocketConversation("/echo") { incoming, outgoing ->
				val textMessages = listOf("HELLO", "WORLD")
				for (msg in textMessages) {
					outgoing.send(Frame.Text(msg))
					assertEquals(msg, (incoming.receive() as Frame.Text).readText())
				}
				assertEquals(textMessages, received)
			}
		}
	}
}