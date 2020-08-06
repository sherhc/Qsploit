package animus.sherhc.qsploit.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class DataFragment(@LayoutRes val layoutId: Int) : Fragment() {
	abstract fun getDataBindingConfig(): DataBindingConfig
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutId, container, false)
		getDataBindingConfig().bindingParams.forEach { key, value ->
			binding.setVariable(key, value)
		}
		binding.lifecycleOwner = viewLifecycleOwner
		return binding.root
	}
}