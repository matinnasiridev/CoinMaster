package ir.groid.coinmaster.apiManager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinsAboutItem(
    var coinWebsite: String? = "There Is No Information!",
    var coinGithub: String? = "There Is No Information!",
    var coinTwitter: String? = "There Is No Information!",
    var coinDes: String? = "There Is No Information!",
    var coinReddit: String? = "There Is No Information!"
) : Parcelable
