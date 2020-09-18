package animus.sherhc.qsploit.pre.network

import android.net.*
import androidx.core.content.getSystemService
import androidx.lifecycle.LiveData
import animus.sherhc.qsploit.App.Companion.app

object NetworkLiveData : LiveData<List<NetworkModel>>() {

	private val request = NetworkRequest.Builder()
		/*  .removeCapability(NetworkCapabilities.NET_CAPABILITY_NOT_VPN)
		  .removeCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
		  .removeCapability(NetworkCapabilities.NET_CAPABILITY_TRUSTED)*/.build()
	private val cm by lazy { requireNotNull(app.getSystemService<ConnectivityManager>()) { "null ConnectivityManager" } }
	private lateinit var networkCallback: NetworkCallback
	override fun onActive() {
		networkCallback = NetworkCallback()
		cm.registerNetworkCallback(request, networkCallback)
	}

	override fun onInactive() {
		cm.unregisterNetworkCallback(networkCallback)
	}

	private class NetworkCallback : ConnectivityManager.NetworkCallback() {
		private val netMap = hashMapOf<Network, NetworkModel>()
		override fun onAvailable(net: Network) {
			val link = cm.getLinkProperties(net)
			val capability = cm.getNetworkCapabilities(net)
			if (link != null && capability != null) {
				netMap[net] = NetworkModel(link, capability)
				postValue(netMap.values.toList())
			}
		}

		override fun onLinkPropertiesChanged(net: Network, link: LinkProperties) {
			netMap[net]?.apply {
				netMap.replace(net, copy(link = link))
				postValue(netMap.values.toList())
			}

		}

		override fun onCapabilitiesChanged(net: Network, capability: NetworkCapabilities) {
			netMap[net]?.apply {
				netMap.replace(net, copy(capability = capability))
				postValue(netMap.values.toList())
			}
		}

		override fun onLost(network: Network) {
			netMap.remove(network)
			postValue(netMap.values.toList())
		}

		override fun onUnavailable() {
			cm.unregisterNetworkCallback(this)
		}
	}
}