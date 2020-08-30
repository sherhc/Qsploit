package animus.sherhc.qsploit.post

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import animus.sherhc.webSocket.WsServerManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.*
import java.util.concurrent.TimeUnit

class PostViewModel : ViewModel() {
	private val okHttpClient = OkHttpClient.Builder()
		.connectTimeout(3, TimeUnit.SECONDS)
		.readTimeout(3, TimeUnit.SECONDS)
		.writeTimeout(3, TimeUnit.SECONDS)
		.build()
	var ws: WebSocket? = null
	val message = MutableLiveData<String>()

	init {
		viewModelScope.launch(Dispatchers.Default) {
			try {
				WsServerManager.manager.connect()
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
		okHttpClient.dispatcher.executorService.shutdown()
		WsServerManager.manager.disconnect()
		ws?.close(1000, "客户端关闭")
	}
}