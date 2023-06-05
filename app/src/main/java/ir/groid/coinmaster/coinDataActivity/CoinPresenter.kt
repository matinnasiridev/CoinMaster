package ir.groid.coinmaster.coinDataActivity


class CoinPresenter : CoinContract.Presenter {
    private var centerView: CoinContract.View? = null
    override fun onAttach(view: CoinContract.View) {
        centerView = view



    }

    override fun onDetach() {
        centerView = null

    }
}