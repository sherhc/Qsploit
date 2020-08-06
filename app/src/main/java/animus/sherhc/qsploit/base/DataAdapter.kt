package animus.sherhc.qsploit.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class DataAdapter<M : IItem> :
	ListAdapter<M, DataAdapter.BindingViewHolder>(object : DiffUtil.ItemCallback<M>() {
		override fun areItemsTheSame(oldItem: M, newItem: M) = oldItem == newItem

		override fun areContentsTheSame(oldItem: M, newItem: M) =
			oldItem.hashCode() == newItem.hashCode()
	}) {

	private var click: ((View, M) -> Unit)? = null

	fun setClick(listen: ((View, M) -> Unit)) {
		click = listen
	}

	override fun getItemViewType(pos: Int) = currentList[pos].getLayout()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		BindingViewHolder.create(
			parent,
			viewType
		)

	override fun onBindViewHolder(holder: BindingViewHolder, pos: Int) {
		holder.bindTo(currentList[pos])
		holder.itemView.setOnClickListener {
			click?.invoke(it, currentList[pos])
		}
	}

	class BindingViewHolder private constructor(private val binding: ViewDataBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bindTo(item: IItem) {
			binding.setVariable(item.getVariableId(), item)
			binding.executePendingBindings()
		}

		companion object {
			fun create(parent: ViewGroup, viewType: Int) =
				BindingViewHolder(
					DataBindingUtil.inflate<ViewDataBinding>(
						LayoutInflater.from(parent.context),
						viewType, parent, false
					)
				)
		}
	}
}