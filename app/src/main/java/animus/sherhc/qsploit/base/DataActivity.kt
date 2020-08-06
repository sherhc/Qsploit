package animus.sherhc.qsploit.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class DataActivity(@LayoutRes val layoutId: Int) : AppCompatActivity() {
	abstract fun getDataBindingConfig(): DataBindingConfig
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val binding =
			DataBindingUtil.setContentView<ViewDataBinding>(this, layoutId)
		getDataBindingConfig().bindingParams.forEach { key, value ->
			binding.setVariable(key, value)
		}
		binding.lifecycleOwner = this
	}
}