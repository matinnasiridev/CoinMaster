package ir.groid.coinmaster.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


fun <T : RecyclerView.ViewHolder?> RecyclerView.fillManager(adapter: () -> RecyclerView.Adapter<T>) {
    this.adapter = adapter()
    this.layoutManager = LinearLayoutManager(this.context)
}