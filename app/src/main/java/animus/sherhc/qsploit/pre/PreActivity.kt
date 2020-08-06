package animus.sherhc.qsploit.pre

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import animus.sherhc.qsploit.R
import kotlinx.android.synthetic.main.activity_pre.*

class PreActivity : AppCompatActivity(R.layout.activity_pre) {
	override fun onCreate(savedInstanceState: Bundle?) {
		setTheme(R.style.AppTheme)
		super.onCreate(savedInstanceState)
		preBar.setupWithNavController(findNavController(R.id.preContainer))
	}
}