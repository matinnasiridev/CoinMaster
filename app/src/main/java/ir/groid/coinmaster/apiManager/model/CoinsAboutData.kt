package ir.groid.coinmaster.apiManager.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

class CoinsAboutData : ArrayList<CoinsAboutData.CoinsAboutDataItem>(){
    @Parcelize
    data class CoinsAboutDataItem(
        @SerializedName("currencyName")
        val currencyName: String?,
        @SerializedName("info")
        val info: Info?
    ) : Parcelable {
        @Parcelize
        data class Info(
            @SerializedName("desc")
            val desc: String?,
            @SerializedName("forum")
            val forum: String?,
            @SerializedName("github")
            val github: String?,
            @SerializedName("reddit")
            val reddit: String?,
            @SerializedName("twt")
            val twt: String?,
            @SerializedName("web")
            val web: String?
        ) : Parcelable
    }
}