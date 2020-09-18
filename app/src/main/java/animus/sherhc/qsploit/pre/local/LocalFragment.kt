package animus.sherhc.qsploit.pre.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.TextFieldValue
import androidx.fragment.app.Fragment
import animus.sherhc.qsploit.theme.AppTheme

class LocalFragment : Fragment() {
	val tool = ScanDeviceTool()
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		tool.scan()
		return ComposeView(requireContext()).apply {
			setContent {
				AppTheme {
					LocalScreen()
				}
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		tool.destroy()
	}

	/*	lifecycleScope.launch(Dispatchers.IO) {
				val dp = DatagramPacket(byteArrayOf(), 0, 0)
				val socket = DatagramSocket()
				//socket.broadcast = true
				var position = 1
				while (position < 255) {
					Log.e("position", "$position")
					dp.address = InetAddress.getByName("192.168.123.$position")
					socket.send(dp)
					position++
				}
				Log.e("finish", "end")
				socket.close()
			}*/
	@Composable
	fun LocalScreen() {

		val text = remember { mutableStateOf(TextFieldValue()) }
		OutlinedTextField(
			value = text.value,
			onValueChange = { text.value = it },
			label = { Text("Label") },
			modifier = Modifier.wrapContentHeight()
		)
	}

}

