package ir.groid.coinmaster.model

import android.os.Parcelable
import ir.groid.coinmaster.util.Constans.NoInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class RCoinAbout(
    var coinWebsite: String? = NoInfo,
    var coinGithub: String? = NoInfo,
    var coinTwitter: String? = NoInfo,
    var coinDes: String? = NoInfo,
    var coinReddit: String? = NoInfo
) : Parcelable
