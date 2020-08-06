package animus.sherhc.qsploit.pre

import android.net.LinkProperties
import android.net.NetworkCapabilities
import android.view.View
import androidx.annotation.LayoutRes
import androidx.navigation.findNavController
import animus.sherhc.qsploit.BR
import animus.sherhc.qsploit.R
import animus.sherhc.qsploit.base.IItem
import java.net.Inet4Address

data class NetworkModel(
	val link: LinkProperties, val capability: NetworkCapabilities
) : IItem {
	override fun getLayout() = R.layout.item_network
	override fun getVariableId() = BR.item

	val interfaceName = link.interfaceName
	val linkAddresses: String = link.linkAddresses.first { it.address is Inet4Address }
		.address.hostAddress
	val dns = link.dnsServers.joinToString("\n", transform =
	{ it.hostAddress })
	val gateway = link.routes.first { it.isDefaultRoute }.gateway?.hostAddress ?: ""

	@LayoutRes
	val type = when {
		capability.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> R.drawable.pre_vpn
		capability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> R.drawable.pre_cellular
		capability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> R.drawable.pre_wifi
		capability.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> R.drawable.pre_btnet
		else -> R.drawable.pre_net
	}

	fun onClick(v: View) {
		v.findNavController()
	}
}