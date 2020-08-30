package animus.sherhc.webSocket

import android.util.Log
import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

class KtorServer : IServer() {
	var engine: ApplicationEngine? = null
	val started: (Application) -> Unit =
		{ Log.e(this.javaClass.simpleName, "Application starting: $it") }

	override fun connect() {
		val env = applicationEngineEnvironment {
			module {
				main()
			}
			connector {
				host = "0.0.0.0"
				port = 8080
			}
		}
		engine = embeddedServer(Netty, env)
		engine?.start(false)
	}

	override fun disconnect() {
		engine?.stop(1000, 1000)
		engine = null
	}

	override var isConnected = false
}