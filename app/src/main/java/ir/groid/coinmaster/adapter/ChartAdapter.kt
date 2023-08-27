package ir.groid.coinmaster.adapter

import com.robinhood.spark.SparkAdapter
import ir.groid.coinmaster.model.RChartData

class ChartAdapter : SparkAdapter() {
    private var historyData: List<RChartData> = listOf()
    private var baseLine: String? = null

    override fun getCount(): Int = historyData.size

    override fun getItem(index: Int): RChartData = historyData[index]

    override fun getY(index: Int): Float {
        return historyData[index].closeP.toFloat()
    }

    override fun hasBaseLine(): Boolean = true

    override fun getBaseLine(): Float {
        return baseLine?.toFloat() ?: super.getBaseLine()
    }

    fun submit(newList: List<RChartData>, newBase: String? = null) {
        historyData = newList
        baseLine = newBase
        notifyDataSetChanged()
    }
}