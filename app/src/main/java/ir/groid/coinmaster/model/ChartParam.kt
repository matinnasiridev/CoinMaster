package ir.groid.coinmaster.model

data class ChartParam(
    val hisToPer: String,
    val limit: Int? = 30,
    val agg: Int? = 1
)
