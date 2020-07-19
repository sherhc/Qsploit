package animus.sherhc.qsploit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
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
    fun connectWebSocket(_url: String) {
        viewModelScope.launch(Dispatchers.Default) {
            ws = ws ?: okHttpClient.newWebSocket(
                Request.Builder().url(_url).build(), object : WebSocketListener() {
                    override fun onMessage(webSocket: WebSocket, text: String) {
                        message.postValue(text)
                    }

                    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                        ws = null
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
    }
}