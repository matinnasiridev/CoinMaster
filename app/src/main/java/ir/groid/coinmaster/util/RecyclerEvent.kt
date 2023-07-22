package ir.groid.coinmaster.util

interface RecyclerEvent<T> {
    fun onClick(data: T)
}