package animus.sherhc.qsploit.adapter

import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import animus.sherhc.qsploit.R
import com.google.android.material.textview.MaterialTextView

object LinearLayoutBindAdapter {
	@JvmStatic
	@BindingAdapter(value = ["list"])
	fun <M> LinearLayout.setList(list: List<M>?) {
		list ?: return
		this.removeAllViews()
		list.forEach {
			val textView = MaterialTextView(context)
			textView.text = "$it"
			TextViewCompat.setTextAppearance(
				textView,
				R.style.TextAppearance_MaterialComponents_Caption
			)
			this.addView(textView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
	}
}