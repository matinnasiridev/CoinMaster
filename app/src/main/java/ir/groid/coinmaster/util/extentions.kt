package ir.groid.coinmaster.util

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


fun <T : RecyclerView.ViewHolder?> RecyclerView.setAdapter(adapter: () -> RecyclerView.Adapter<T>) {
    this.adapter = adapter()
    this.layoutManager = LinearLayoutManager(this.context)
}

fun Context.showToast(s: String) {
    Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}

fun <T> Single<T>.thereadHandeler(): Single<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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