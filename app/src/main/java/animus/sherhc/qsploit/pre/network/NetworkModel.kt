package animus.sherhc.qsploit.pre.network

import android.net.LinkAddress
import android.net.LinkProperties
import android.net.NetworkCapabilities
import androidx.annotation.DrawableRes
import animus.sherhc.qsploit.R

data class NetworkModel(
	val link: LinkProperties, val capability: NetworkCapabilities
) {
	val interfaceName = link.interfaceName
	val linkAddresses: List<LinkAddress> = link.linkAddresses

	val dns = link.dnsServers.map { it.hostAddress }
	val gateway = link.routes.map { it.gateway?.hostAddress }.toSet().toList()

	@DrawableRes
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

