package animus.sherhc.qsploit.pre.local

import android.net.LinkAddress
import java.math.BigInteger
import kotlin.math.pow

data class NetMaskModel(val linkAddress: LinkAddress) {
	private val hostAddress: Int = BigInteger(linkAddress.address.address).toInt()
	private val prefix: Int = linkAddress.prefixLength
	val netMask: Int = if (prefix > 0) ((0xffffffff shl (32 - prefix)) ushr 0).toInt() else 0
	private val base: Int = hostAddress and netMask
	private val subNetSize: Int = 2.0.pow(32 - prefix).toInt()
	val first = if (prefix <= 30) base + 1 else base
	val last = if (prefix <= 30) (base + subNetSize - 2) else (base + subNetSize - 1)
	val broadcast = if (prefix <= 30) (base + subNetSize - 1) else null
}