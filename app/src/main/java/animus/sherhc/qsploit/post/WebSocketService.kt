package animus.sherhc.qsploit.post

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import animus.sherhc.qsploit.R
import animus.sherhc.webSocket.WebSocketListener
import animus.sherhc.webSocket.WebSocketServer
import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.launch


class WebSocketService : LifecycleService() {
	private var server: WebSocketServer? = null
	val message = MutableLiveData<String>()

	override fun onBind(intent: Intent): IBinder? {
		super.onBind(intent)
		return object : Binder() {

		}
	}

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		super.onStartCommand(intent, flags, startId)
		createNotificationChannel()
		intent?.action?.run {
			if (this != "stop") {
				Log.e("$this@WebSocketService", "start")
				lifecycleScope.launch {
					try {
						server =
							WebSocketServer(object : WebSocketListener<WebSocketServerSession>() {
								override fun onOpen(session: WebSocketServerSession) {
									Log.e("open", "open")
								}

								override fun onMessage(
									session: WebSocketServerSession,
									frame: Frame
								) {
									if (frame is Frame.Text) {
										val a = frame.readText()
										Log.e("onMessage", a)
										message.postValue(a)
									}
								}

								override fun onClose(session: WebSocketServerSession) {

									Log.e("onclose", "onclose")
								}

								override fun onError(
									session: WebSocketServerSession,
									err: Exception
								) {
									Log.e("onError", err.message, err)
								}
							})
						server?.start()
						Log.e("server", "start")
					} catch (e: Exception) {
						Log.e(this.javaClass.simpleName, "$e")
					}
				}
			} else {
				stopSelf()
			}

		}
		return START_STICKY
	}

	override fun onDestroy() {

		Log.e("$this", "结束")
		stopForeground(true)
		super.onDestroy()
	}

	private fun createNotificationChannel() {
		val mNotificationManager = getSystemService<NotificationManager>()
		// 通知渠道的id
		val id = "my_channel_01"
		// 用户可以看到的通知渠道的名字.
		val name = "test"
		//         用户可以看到的通知渠道的描述

		val importance = NotificationManager.IMPORTANCE_HIGH
		val mChannel = NotificationChannel(id, name, importance)
		//         配置通知渠道的属性
		mNotificationManager?.createNotificationChannel(mChannel)

		// 为该通知设置一个id
		val notifyID = 1
		// 通知渠道的id
		// Create a notification and set the notification channel.
		val notification = NotificationCompat.Builder(this, id)
			.setSmallIcon(R.drawable.ic_dvr)
			.setContentTitle("服务器已开启")
			.setContentText("开放端口8080")
			.setPriority(NotificationCompat.PRIORITY_DEFAULT)
			.addAction(
				R.drawable.ic_done,
				"启动",
				PendingIntent.getService(
					this,
					0,
					Intent("stop", null, this, WebSocketService::class.java),
					PendingIntent.FLAG_CANCEL_CURRENT
				)
			)
			.build()
		startForeground(notifyID, notification)
	}

}