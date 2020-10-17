package animus.sherhc.qsploit.pre.network

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.longPressGestureFilter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import animus.sherhc.qsploit.R
import animus.sherhc.qsploit.theme.AppTheme
import java.net.InetAddress


class NetworkFragment : Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		state: Bundle?
	) = ComposeView(requireContext()).apply {
		setContent {
			AppTheme {
				NetworkScreen()
			}
		}
	}

	@Composable
	fun NetworkScreen() {
		Scaffold(
			floatingActionButton = {
				ExtendedFloatingActionButton(
					text = { Text("搜寻网络") },
					onClick = {
						findNavController().navigate(R.id.preToPost)
					},
					icon = {
						Icon(Icons.Outlined.Add)
					},
				)
			},
			bodyContent = {
				NetworkList(NetworkLiveData)
			}
		)
	}

	@Composable
	fun NetworkList(data: LiveData<List<NetworkModel>>) {
		val list by data.observeAsState(emptyList())
		LazyColumnFor(list) { networkModel ->
			Card(
				elevation = 2.dp,
				modifier = Modifier.padding(8.dp)
					.fillMaxWidth()
					.longPressGestureFilter {}
			) {
				Column {
					ListItem(
						icon = {
							Icon(
								vectorResource(networkModel.type),
								tint = MaterialTheme.colors.primary,
							)
						},
						text = { Text(networkModel.interfaceName ?: "N/A") },
					)
					networkModel.linkAddresses.forEach {
						ListItem(
							overlineText = { Text(getType(it.address)) },
							text = { Text(it.address.hostAddress) }
						)
					}
				}
			}
		}
	}

	private fun getType(inetAddress: InetAddress): String {
		return when {
			inetAddress.isLinkLocalAddress -> "LinkLocal"
			inetAddress.isSiteLocalAddress -> "SiteLocal"
			inetAddress.isAnyLocalAddress -> "AnyLocal"
			inetAddress.isLoopbackAddress -> "Loopback"
			inetAddress.isMCGlobal -> "MCGlobal"
			inetAddress.isMCLinkLocal -> "MCLinkLocal"
			inetAddress.isMCNodeLocal -> "MCNodeLocal"
			inetAddress.isMCOrgLocal -> "MCOrgLocal"
			inetAddress.isMCSiteLocal -> "MCSiteLocal"
			inetAddress.isMulticastAddress -> "MulticastAddress"
			else -> ""
		}
	}

}

