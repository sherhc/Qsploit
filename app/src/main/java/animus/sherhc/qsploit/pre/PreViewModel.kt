package animus.sherhc.qsploit.pre

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import animus.sherhc.qsploit.R
import animus.sherhc.qsploit.pre.network.NetworkLiveData

class PreViewModel : ViewModel() {
	val networkLiveData = NetworkLiveData
	fun moveToPost(view: View) {
		view.findNavController().navigate(R.id.preToPost)
	}
}