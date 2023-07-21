package ir.groid.coinmaster.adapter

import com.robinhood.spark.SparkAdapter
import ir.groid.coinmaster.responce.ChartData

class ChartAdapter(
    private val historyData: List<ChartData.Data>,
    private val baseLine: String?
) : SparkAdapter() {
    override fun getCount(): Int = historyData.size

    override fun getItem(index: Int): ChartData.Data = historyData[index]

    override fun getY(index: Int): Float {
        return historyData[index].close.toFloat()
    }

    override fun hasBaseLine(): Boolean = true

    override fun getBaseLine(): Float {
        return baseLine?.toFloat() ?: super.getBaseLine()
    }

}