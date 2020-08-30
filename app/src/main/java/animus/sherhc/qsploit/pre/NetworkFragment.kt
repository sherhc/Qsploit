package animus.sherhc.qsploit.pre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

class NetworkFragment : Fragment() {
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	) = ComposeView(context = requireContext()).apply {
		setContent {
			NetworkScreen()
		}
	}

	@Composable
	fun NetworkScreen() {
		Stack {
			NetworkList(NetworkLiveData)
			FloatingActionButton(
				modifier = Modifier.gravity(Alignment.BottomEnd),
				onClick = {},
				icon = { Icons.Outlined.Person }
			)
		}
	}
}

@Composable
fun NetworkList(data: LiveData<List<NetworkModel>>) {
	val list by data.observeAsState(emptyList())
	LazyColumnFor(list) { networkModel ->
		Card {
			ConstraintLayout() {

			}
		}
		ListItem(text = { Text(networkModel.interfaceName ?: "") })
	}
}