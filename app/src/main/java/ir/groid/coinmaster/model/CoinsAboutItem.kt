package ir.groid.coinmaster.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinsAboutItem(
    var coinWebsite: String? = "",
    var coinGithub: String? = "",
    var coinTwitter: String? = "",
    var coinDes: String? = "There Is No Information!",
    var coinReddit: String? = ""
) : Parcelable
