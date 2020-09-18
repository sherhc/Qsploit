package animus.sherhc.webSocket

import io.ktor.application.*
import io.ktor.http.cio.websocket.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException

class KtorServer(private val listener: WebSocketListener<WebSocketServerSession>) :
	ServerImpl<WebSocketServerSession>() {
	private var engine: ApplicationEngine? = null
	lateinit var currentSession: WebSocketServerSession
	override fun start() {
		val env = applicationEngineEnvironment {
			module {
				install(WebSockets)
				/*install(ContentNegotiation) {
					json(contentType = ContentType.Application.ProtoBuf)
				}*/
				routing {
					webSocket("/") {
						currentSession = this
						listener.onOpen(this)
						try {
							for (frame in incoming) {
								listener.onMessage(this, frame)
							}
						} catch (e: ClosedReceiveChannelException) {
							listener.onClose(this)
						} catch (e: Exception) {
							listener.onError(this, e)
							//Log.e("onError", "${closeReason.await()}")
						}
					}
				}
			}
			connector {
				host = "0.0.0.0"
				port = 8080
			}
		}
		engine = embeddedServer(Netty, env)
		engine?.start(false)
	}

	override fun getRemoteIp(session: WebSocketServerSession) = session.call.request.host()


	override fun close(session: WebSocketServerSession, code: Int, reason: String) {
		engine?.stop(1000, 1000)
		engine = null
	}

	override suspend fun send(text: String) {
		currentSession.send(Frame.Text(text))
	}

	override suspend fun send(byte: ByteArray) {
		currentSession.send(Frame.Binary(false, byte))
	}
}