package animus.sherhc.qsploit

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity(R.layout.activity_post) {
    private val viewModel by viewModels<PostViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        viewModel.message.observe(this) {
            output.append("$it\n")
        }
        inputLayout.setEndIconOnClickListener {
            viewModel.ws?.send("${input.text}")
        }
        viewModel.connectWebSocket("wss://echo.websocket.org")
    }
}