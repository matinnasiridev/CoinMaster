package ir.groid.coinmaster.viewModels

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ir.groid.coinmaster.R
import ir.groid.coinmaster.repository.AppRepository

class CoinDataVM(private val repository: AppRepository) : ViewModel() {
    private val dis = CompositeDisposable()

    fun getPeriod(id: Int): String {
        return when (id) {
            R.id.radio_all -> "adwadaw"
            else -> ""
        }
    }

    fun getDis(d: Disposable) = dis.add(d)

    override fun onCleared() {
        super.onCleared()
        dis.dispose()
    }
}