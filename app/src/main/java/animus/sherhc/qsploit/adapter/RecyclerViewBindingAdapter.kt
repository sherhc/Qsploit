package animus.sherhc.qsploit.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import animus.sherhc.qsploit.base.DataAdapter
import animus.sherhc.qsploit.base.IItem


object RecyclerViewBindingAdapter {
	@JvmStatic
	@BindingAdapter(value = ["list"])
	fun <M : IItem> RecyclerView.setList(list: List<M>?) {
		list ?: return
		val adapter = object : DataAdapter<M>() {}
		adapter.submitList(list)
		this.adapter = adapter
	}

	@JvmStatic
	@BindingAdapter(value = ["decoration"])
	fun RecyclerView.bindItemDecoration(bind: Boolean) {
		if (bind)
			addItemDecoration(
				DividerItemDecoration(
					context,
					LinearLayoutManager.VERTICAL
				)
			)
	}
}