package ir.groid.coinmaster.model


import ir.groid.coinmaster.util.Constans.HISTO_MINUTE

/**
 * Def Set For HOUR
 */
data class ChartParam(
    val hisToPer: String? = HISTO_MINUTE,
    val limit: Int? = 30,
    val agg: Int? = 1
)
