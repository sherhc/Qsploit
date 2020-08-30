package animus.sherhc.qsploit.pre

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import animus.sherhc.qsploit.R

class ClickProxy {
	init {
		Log.e("1", this.toString())
	}

	fun click1(view: View, item: NetworkModel) {
		Toast.makeText(view.context, item.dns.toString(), Toast.LENGTH_SHORT).show()
	}

	fun click2(view: View, item: NetworkModel) {
		Toast.makeText(view.context, item.gateway.toString(), Toast.LENGTH_SHORT).show()
	}

	fun click3(view: View) {
		view.findNavController().navigate(R.id.scan)
	}
}