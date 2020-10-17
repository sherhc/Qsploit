package animus.sherhc.qsploit

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {
	companion object {
		lateinit var app: App
			private set
	}

	override fun onCreate() {
		super.onCreate()
		app = this
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
	}
}