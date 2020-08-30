package animus.sherhc.qsploit.post

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import animus.sherhc.qsploit.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView

class PostActivity : AppCompatActivity(R.layout.activity_post) {
	private val viewModel by viewModels<PostViewModel>()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val outText = findViewById<MaterialTextView>(R.id.outText)
		val inputLayout = findViewById<TextInputLayout>(R.id.inputLayout)
		val inputText = findViewById<TextInputEditText>(R.id.inputText)
		viewModel.message.observe(this, { outText.append("server: $it\n") })
		inputLayout.setEndIconOnClickListener {
			viewModel.ws?.send("${inputText.text}")
			outText.append("client: ${inputText.text}\n")
		}
		//viewModel.connectWebSocket("ws://192.168.223.11:8876")
		viewModel.connectWebSocket("ws://0.0.0.0:8080")
	}
}