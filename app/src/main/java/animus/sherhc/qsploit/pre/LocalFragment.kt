package animus.sherhc.qsploit.pre

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import animus.sherhc.qsploit.R

class LocalFragment : Fragment(R.layout.fragment_local) {
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
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


	}
}