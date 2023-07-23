package ir.groid.coinmaster.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RCoinAbout(
    var coinWebsite: String? = null,
    var coinGithub: String? = null,
    var coinTwitter: String? = null,
    var coinDes: String? = null,
    var coinReddit: String? = null
) : Parcelable
