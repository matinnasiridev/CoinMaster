package ir.groid.coinmaster.apiManager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinsAboutItem(
    var coinWebsite: String? = "No data found",
    var coinGithub: String? = "No data found",
    var coinTwitter: String? = "No data found",
    var coinDes: String? = "No data found",
    var coinReddit: String? = "No data found"
) : Parcelable
