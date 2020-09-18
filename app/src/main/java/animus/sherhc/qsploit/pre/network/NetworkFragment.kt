package animus.sherhc.qsploit.pre.network

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.ClickableText
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import animus.sherhc.qsploit.R
import animus.sherhc.qsploit.theme.AppTheme

class NetworkFragment : Fragment() {
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, state: Bundle?
	) = ComposeView(requireContext()).apply {
		setContent {
			//NetworkScreen()
			AppTheme {
				NetworkScreen()
			}
		}
	}

	@Composable
	fun NetworkScreen() {
		Stack {
			NetworkList(NetworkLiveData)
			FloatingActionButton(
				onClick = {
					findNavController().navigate(R.id.preToPost)
				},
				icon = {
					Icon(Icons.Outlined.Add)
				},
				modifier = Modifier
					.gravity(Alignment.BottomEnd)
					.padding(16.dp)
					.preferredHeight(48.dp)
					.widthIn(minWidth = 48.dp),
			)
		}
	}

	@Composable
	fun NetworkList(data: LiveData<List<NetworkModel>>) {
		val list by data.observeAsState(emptyList())
		LazyColumnFor(list) { networkModel ->
			Card(
				modifier = Modifier.padding(8.dp)
					.fillMaxSize(),

				) {
				Column {
					Image(
						vectorResource(networkModel.type),
						modifier = Modifier.gravity(Alignment.CenterHorizontally)
							.size(72.dp)
					)
					Text(networkModel.interfaceName ?: "")
					networkModel.linkAddresses.forEach { linkAddress ->
						ClickableText(
							text = AnnotatedString(linkAddress ?: "N/a"),
							onClick = { findNavController().navigate(R.id.scan) }
						)
					}

				}

			}
		}
	}

}

