package animus.sherhc.qsploit.pre

import android.net.LinkProperties
import android.net.NetworkCapabilities
import androidx.annotation.LayoutRes
import animus.sherhc.qsploit.BR
import animus.sherhc.qsploit.R
import animus.sherhc.qsploit.base.IItem

data class NetworkModel(
	val link: LinkProperties, val capability: NetworkCapabilities
) : IItem {
	override fun getLayout() = R.layout.item_network
	override fun getVariableId() = BR.item
	override fun getProxy() = BR.proxy

	val interfaceName = link.interfaceName
	val linkAddresses = link.linkAddresses.map { it.address.hostAddress }
	val dns = link.dnsServers.map { it.hostAddress }
	val gateway = link.routes.map { it.gateway?.hostAddress }.toSet().toList()

	@LayoutRes
	val type = when {
		capability.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> R.drawable.pre_vpn
		capability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> R.drawable.pre_cellular
		capability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> R.drawable.pre_wifi
		capability.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> R.drawable.pre_btnet
		capability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> R.drawable.pre_ethernet
		capability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE) -> R.drawable.pre_wifi
		else -> R.drawable.pre_net
	}
}

