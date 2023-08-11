package ir.groid.coinmaster.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RCoinAbout(
    var coinWebsite: String? = "There is no information",
    var coinGithub: String? = "There is no information",
    var coinTwitter: String? = "There is no information",
    var coinDes: String? = "There is no information",
    var coinReddit: String? = "There is no information"
) : Parcelable
