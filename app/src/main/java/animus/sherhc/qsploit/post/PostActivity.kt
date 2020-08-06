package animus.sherhc.qsploit.post

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import animus.sherhc.qsploit.R
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity(R.layout.activity_post) {
	private val viewModel by viewModels<PostViewModel>()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel.message.observe(this) {
			output.append("server: $it\n")
		}
		inputLayout.setEndIconOnClickListener {
			viewModel.ws?.send("${input.text}")
			output.append("client: ${input.text}\n")
		}
		//adbviewModel.connectWebSocket("ws://192.168.223.11:8876")
		viewModel.connectWebSocket("wss://echo.websocket.org")
	}
}