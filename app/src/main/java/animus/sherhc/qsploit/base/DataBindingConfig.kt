package animus.sherhc.qsploit.base

import android.util.SparseArray

class DataBindingConfig {
	val bindingParams: SparseArray<Any> = SparseArray()

	fun addBindingParam(variableId: Int, variable: Any): DataBindingConfig {
		bindingParams.put(variableId, variable)
		return this
	}
}