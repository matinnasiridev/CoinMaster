package ir.groid.coinmaster.adapter

import com.robinhood.spark.SparkAdapter

class ChartAdapter : SparkAdapter() {
    private var list: List<Double> = listOf()

    override fun getCount(): Int = list.size

    override fun getItem(index: Int): Double = list[index]

    override fun getY(index: Int): Float {
        return list[index].toFloat()
    }

    fun submit(newList: List<Double>) {
        list = newList
        notifyDataSetChanged()
    }
}