package ir.groid.coinmaster.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import ir.groid.coinmaster.di.AppService

class GlideImageLoader : AppService.ImageLoader {
    override fun loader(url: String, view: ImageView) {
        Glide
            .with(view.context)
            .load(url)
            .into(view)
    }
}