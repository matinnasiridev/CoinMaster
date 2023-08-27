package ir.groid.coinmaster.viewModels


import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ir.groid.coinmaster.R
import ir.groid.coinmaster.model.ChartParam
import ir.groid.coinmaster.model.RChartData
import ir.groid.coinmaster.repository.AppRepository
import ir.groid.coinmaster.util.Constans.ALL
import ir.groid.coinmaster.util.Constans.HISTO_DAY
import ir.groid.coinmaster.util.Constans.HISTO_HOUR
import ir.groid.coinmaster.util.Constans.HISTO_MINUTE
import ir.groid.coinmaster.util.Constans.HOUR
import ir.groid.coinmaster.util.Constans.HOURS24
import ir.groid.coinmaster.util.Constans.MONTH
import ir.groid.coinmaster.util.Constans.MONTH3
import ir.groid.coinmaster.util.Constans.WEEK
import ir.groid.coinmaster.util.Constans.YEAR

class CoinDataVM(private val repository: AppRepository) : ViewModel() {
    private val dis = CompositeDisposable()

    private fun getPeriod(id: Int?): String {
        return when (id) {
            R.id.radio_12h -> HOUR
            R.id.radio_1d -> HOURS24
            R.id.radio_1w -> WEEK
            R.id.radio_1m -> MONTH
            R.id.radio_3m -> MONTH3
            R.id.radio_1y -> YEAR
            R.id.radio_all -> ALL
            else -> HOUR
        }
    }

    private fun otherOptionUsePer(id: Int?): ChartParam {
        return when (getPeriod(id)) {
            HOUR -> ChartParam(hisToPer = HISTO_MINUTE, limit = 60, agg = 12)
            HOURS24 -> ChartParam(hisToPer = HISTO_HOUR, limit = 24)
            WEEK -> ChartParam(hisToPer = HISTO_HOUR, agg = 6)
            MONTH -> ChartParam(hisToPer = HISTO_DAY, limit = 30)
            MONTH3 -> ChartParam(hisToPer = HISTO_DAY, limit = 90)
            YEAR -> ChartParam(hisToPer = HISTO_DAY, agg = 13)
            ALL -> ChartParam(hisToPer = HISTO_DAY, limit = 2000, agg = 30)
            // HOUR For Def Value
            else -> ChartParam(hisToPer = HISTO_MINUTE, limit = 60, agg = 12)
        }
    }

    fun getChartData(
        symbol: String,
        id: Int? = null
    ): Single<List<RChartData>> {
        return repository.getAllChartsPoint(otherOptionUsePer(id), symbol)
    }

    fun getDis(d: Disposable) = dis.add(d)

    override fun onCleared() {
        super.onCleared()
        dis.dispose()
    }
}