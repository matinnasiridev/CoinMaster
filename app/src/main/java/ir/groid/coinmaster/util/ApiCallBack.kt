package ir.groid.coinmaster.util

interface ApiCallBack<T> {
    fun onSuccess(data: T)
    fun onError(em: String)
}