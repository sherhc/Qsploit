package animus.sherhc.qsploit.pre

import androidx.fragment.app.viewModels
import animus.sherhc.qsploit.BR
import animus.sherhc.qsploit.R
import animus.sherhc.qsploit.base.DataBindingConfig
import animus.sherhc.qsploit.base.DataFragment

class NetworkFragment : DataFragment(R.layout.fragment_network) {
	private val viewModel by viewModels<PreViewModel>()
	override fun getDataBindingConfig() =
		DataBindingConfig()
			.addBindingParam(BR.viewModel, viewModel)
}