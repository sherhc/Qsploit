package animus.sherhc.qsploit.base

open class SingletonHolder<out T, in A>(creator: ((A) -> T)) {
	private var _creator: ((A) -> T)? = creator

	@Volatile
	private var instance: T? = null

	fun getInstance(arg: A): T {
		return instance ?: synchronized(this) {
			val i2 = instance
			if (i2 != null) {
				i2
			} else {
				val created = _creator!!(arg)
				instance = created
				_creator = null
				created
			}
		}
	}
}