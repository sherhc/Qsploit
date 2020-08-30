package animus.sherhc.qsploit.pre

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import animus.sherhc.qsploit.R
import com.google.android.material.appbar.MaterialToolbar

class PreActivity : AppCompatActivity(R.layout.activity_pre) {
	override fun onCreate(savedInstanceState: Bundle?) {
		setTheme(R.style.AppTheme)
		super.onCreate(savedInstanceState)
		val preBar = findViewById<MaterialToolbar>(R.id.preBar)
		val preDrawer = findViewById<DrawerLayout>(R.id.preDrawer)
		preBar.setupWithNavController(findNavController(R.id.preContainer), preDrawer)

	}

}