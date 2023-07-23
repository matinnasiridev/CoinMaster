package ir.groid.coinmaster.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ComplexColorCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.groid.coinmaster.R
import ir.groid.coinmaster.databinding.ItemMarketSmallBinding
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.util.RecyclerEvent


class MarketAdapter(
    private val event: RecyclerEvent<RCoinData>,
    private val listCoins: List<RCoinData>
) :
    RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {
    private lateinit var binding: ItemMarketSmallBinding

    inner class MarketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(data: RCoinData) {

            binding.apply {
                txtCoinName.text = data.txtCoinName
                txtPrice.text = data.txtPrice
                txtMarketCap.text = data.txtMarketCap

                txtTaghir.text = setColor(data.txtTaghir!!.toInt())
            }



            itemView.setOnClickListener { event.onClick(data) }
        }

        private val setColor: (s: Int) -> String = { it.toString() }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        binding = ItemMarketSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarketViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        holder.bind(listCoins[position])
    }

    override fun getItemCount(): Int = listCoins.size


}

