package animus.sherhc.qsploit.post

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import animus.sherhc.webSocket.WebSocketServer
import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.*
import java.util.concurrent.TimeUnit
import animus.sherhc.webSocket.WebSocketListener as Listener

class PostViewModel : ViewModel() {
	private val okHttpClient = OkHttpClient.Builder()
		.connectTimeout(3, TimeUnit.SECONDS)
		.readTimeout(3, TimeUnit.SECONDS)
		.writeTimeout(3, TimeUnit.SECONDS)
		.build()
	var ws: WebSocket? = null
	var server: WebSocketServer? = null
	val message = MutableLiveData<String>()

	init {
		viewModelScope.launch(Dispatchers.Default) {
			try {
				server = WebSocketServer(object : Listener<WebSocketServerSession>() {
					override fun onMessage(session: WebSocketServerSession, frame: Frame) {
						if (frame is Frame.Text) {
							message.postValue(frame.readText())
						}
					}
				})
				server?.start()
			} catch (e: Exception) {
				Log.e(this@PostViewModel.javaClass.simpleName, "$e")
			}
		}
	}


	fun connectWebSocket(_url: String) {
		viewModelScope.launch(Dispatchers.Default) {
			delay(2000L)
			ws = ws ?: okHttpClient.newWebSocket(
				Request.Builder().url(_url).build(), object : WebSocketListener() {
					override fun onMessage(webSocket: WebSocket, text: String) {
						message.postValue(text)
					}

					override fun onFailure(
						webSocket: WebSocket,
						t: Throwable,
						response: Response?
					) {
						message.postValue("$t")
						ws = null
					}
				})
		}
	}

	override fun onCleared() {
		super.onCleared()
		//WsServerManager.manager.close()
		okHttpClient.dispatcher.executorService.shutdown()
		ws?.close(1000, "客户端关闭")
	}
}