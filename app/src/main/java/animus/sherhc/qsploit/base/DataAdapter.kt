package animus.sherhc.qsploit.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import animus.sherhc.qsploit.pre.ClickProxy

abstract class DataAdapter<M : IItem> :
	ListAdapter<M, DataAdapter.BindingViewHolder>(object : DiffUtil.ItemCallback<M>() {
		override fun areItemsTheSame(oldItem: M, newItem: M) = oldItem == newItem

		override fun areContentsTheSame(oldItem: M, newItem: M) =
			oldItem.hashCode() == newItem.hashCode()
	}) {

	override fun getItemViewType(pos: Int) = currentList[pos].getLayout()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		BindingViewHolder(
			DataBindingUtil.inflate<ViewDataBinding>(
				LayoutInflater.from(parent.context),
				viewType, parent, false
			)
		)

	override fun onBindViewHolder(holder: BindingViewHolder, pos: Int) {
		holder.bindTo(currentList[pos])
	}

	class BindingViewHolder(private val binding: ViewDataBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bindTo(item: IItem) {
			binding.setVariable(item.getVariableId(), item)
			binding.setVariable(item.getProxy(), ClickProxy())
			binding.executePendingBindings()
		}
	}
}