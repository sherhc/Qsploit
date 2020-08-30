package animus.sherhc.qsploit.base

import androidx.annotation.LayoutRes

interface IItem {
	@LayoutRes
	fun getLayout(): Int

	fun getVariableId(): Int

	fun getProxy(): Int
}