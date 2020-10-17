package animus.sherhc.qsploit.pre.local

import android.text.TextUtils
import android.util.Log
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern


class LocalManager {
	companion object {
		private val tag = LocalManager::class.java.simpleName

		/**
		 * 核心池大小
		 */
		private const val CORE_POOL_SIZE = 1

		/**
		 * 线程池最大线程数
		 */
		private const val MAX_NUM_POOL_SIZE = 255
	}

	private val mPing = "ping -c 1 -w 3 " // 其中 -c 1为发送的次数，-w 表示发送后等待响应的时间
	private var mDevAddress // 本机IP地址-完整
			= "192.168.223.11"
	private var mLocAddress // 局域网IP地址头,如：192.168.1.
			: String? = null
	private val mRun = Runtime.getRuntime() // 获取当前运行环境，来执行ping，相当于windows的cmd

	private var mProcess: Process? = null // 进程


	private val mIpList: MutableList<String> = ArrayList() // ping成功的IP地址

	private var mExecutor: ThreadPoolExecutor? = null// 线程池对象
	private var mFlag = true

	/**
	 * TODO<扫描局域网内ip></扫描局域网内ip>，找到对应服务器>
	 *
	 * @return void
	 */
	@Synchronized
	fun scan() {
		if (mFlag) {
			mIpList.clear()
			mFlag = false
			mLocAddress = getLocAddrIndex(mDevAddress) // 获取本地ip前缀
			Log.e(tag, "开始扫描设备,本机Ip为：$mDevAddress")
			if (TextUtils.isEmpty(mLocAddress)) {
				Log.e(tag, "扫描失败，请检查wifi网络")
				return
			}
			/**
			 * 1.核心池大小 2.线程池最大线程数 3.表示线程没有任务执行时最多保持多久时间会终止
			 * 4.参数keepAliveTime的时间单位，有7种取值,当前为毫秒
			 * 5.一个阻塞队列，用来存储等待执行的任务，这个参数的选择也很重要，会对线程池的运行过程产生重大影响
			 * ，一般来说，这里的阻塞队列有以下几种选择：
			 */
			mExecutor = ThreadPoolExecutor(
				CORE_POOL_SIZE, MAX_NUM_POOL_SIZE,
				2000, TimeUnit.MILLISECONDS, ArrayBlockingQueue(
					CORE_POOL_SIZE
				)
			)
			// 新建线程池
			for (i in 1..254) { // 创建256个线程分别去ping
				val run = Runnable {

					val ping = (mPing + mLocAddress
							+ i)
					val currentIp = mLocAddress + i
					if (mDevAddress == currentIp) // 如果与本机IP地址相同,跳过
						return@Runnable
					try {
						mProcess = mRun.exec(ping)
						val result = mProcess?.waitFor()
						Log.e(tag, "正在扫描的IP地址为：$currentIp,返回值为：$result")
						if (result == 0) {
							mIpList.add(currentIp)
							Log.e(
								tag,
								"扫描成功,Ip地址为：" + mIpList.size + "个设备:" + currentIp + ":" + getHardwareAddress(
									currentIp
								)
							)
						}
					} catch (e: Exception) {
						Log.e(tag, "扫描异常$e")
					} finally {
						mProcess?.destroy()
					}
				}
				try {
					mExecutor?.execute(run)
				} catch (e: Exception) {
					e.printStackTrace()
				}
			}
			mExecutor?.shutdown()
			while (true) {
				try {
					if (mExecutor!!.isTerminated) { // 扫描结束,开始验证
						Log.e(tag, "扫描结束,总共成功扫描到" + mIpList.size + "个设备.")
						break
					}
				} catch (e: Exception) {
					// TODO: handle exception
				}
				try {
					Thread.sleep(1000)
				} catch (e: InterruptedException) {
					// TODO Auto-generated catch block
					e.printStackTrace()
				}
			}
		}
	}

	/**
	 * TODO<销毁正在执行的线程池>
	 *
	 * @return void
	</销毁正在执行的线程池> */
	fun destroy() {
		if (mExecutor != null) {
			mExecutor?.shutdownNow()
			mFlag = true
		}
	}

	fun getGetList(): List<String>? {
		return mIpList
	}


	/**
	 * TODO<获取本地ip地址>
	 *
	 * @return String
	</获取本地ip地址> */
	private fun getLocAddress(): String? {
		var ipaddress = ""
		try {
			val en = NetworkInterface
				.getNetworkInterfaces()
			// 遍历所用的网络接口
			while (en.hasMoreElements()) {
				val networks = en.nextElement()
				// 得到每一个网络接口绑定的所有ip
				val address = networks.inetAddresses
				// 遍历每一个接口绑定的所有ip
				while (address.hasMoreElements()) {
					val ip = address.nextElement()
					if (!ip.isLoopbackAddress
						&& ip is Inet4Address
					) {
						ipaddress = ip.hostAddress
					}
				}
			}
		} catch (e: SocketException) {
			Log.e("", "获取本地ip地址失败")
			e.printStackTrace()
		}
		Log.i(tag, "本机IP:$ipaddress")
		return ipaddress
	}

	/**
	 * TODO<获取本机IP前缀>
	 *
	 * @param devAddress // 本机IP地址
	 * @return String
	</获取本机IP前缀> */
	private fun getLocAddrIndex(devAddress: String?): String? {
		return if (devAddress != "") {
			devAddress!!.substring(0, devAddress.lastIndexOf(".") + 1)
		} else null
	}

	fun getHardwareAddress(ip: String?): String {
		val MAC_RE = "^%s\\s+0x1\\s+0x2\\s+([:0-9a-fA-F]+)\\s+\\*\\s+\\w+$"
		val BUF = 8 * 1024
		var hw: String = "00:00:00:00:00:00"
		try {
			if (ip != null) {
				val ptrn = String.format(MAC_RE, ip.replace(".", "\\."))
				val pattern: Pattern = Pattern.compile(ptrn)
				val bufferedReader = BufferedReader(FileReader("/proc/net/arp"), BUF)
				var line: String
				var matcher: Matcher
				while (bufferedReader.readLine().also { line = it } != null) {
					matcher = pattern.matcher(line)
					if (matcher.matches()) {
						hw = matcher.group(1) ?: "00:00:00:00:00:00"
						break
					}
				}
				bufferedReader.close()
			} else {
				Log.e(tag, "ip is null")
			}
		} catch (e: IOException) {
			throw Exception("Can't open/read file ARP")
		}
		return hw
	}
}