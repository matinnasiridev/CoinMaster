package ir.groid.coinmaster.util


import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ir.groid.coinmaster.util.Constans.NoInfo


fun <T : RecyclerView.ViewHolder?> RecyclerView.setAdapter(adapter: () -> RecyclerView.Adapter<T>) {
    this.adapter = adapter()
    this.layoutManager = LinearLayoutManager(this.context)
}

fun Completable.thereadHandeler(): Completable {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun ImageView.load(url: String) {
    Glide
        .with(this.context)
        .load(url)
        .into(this)
}

fun TextView.lunch(url: String?) {
    if (url?.contains("https://") == true) this.text = url else this.text = NoInfo
    this.setOnClickListener {
        if (!url.isNullOrEmpty() && "https://" in url)
            this.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}

fun MaterialButton.open(url: String?) {
    this.setOnClickListener {
        if (!url.isNullOrEmpty())
            this.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}