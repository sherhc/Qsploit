package animus.sherhc.qsploit.pre

import android.net.LinkAddress
import java.math.BigInteger
import kotlin.math.pow

data class NetMaskModel(val linkAddress: LinkAddress) {
	private val hostAddress = BigInteger(linkAddress.address.address).toInt()
	private val prefix = linkAddress.prefixLength
	val netmask = if (prefix > 0) ((0xffffffff shl (32 - prefix)) ushr 0).toInt() else 0
	private val base = hostAddress and netmask
	private val size = 2.0.pow(32 - prefix).toInt()
	val first = if (prefix <= 30) base + 1 else base
	val last = if (prefix <= 30) (base + size - 2) else (base + size - 1)
	val broadcast = if (prefix <= 30) (base + size - 1) else null
}