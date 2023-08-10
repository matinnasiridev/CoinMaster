package ir.groid.coinmaster.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ir.groid.coinmaster.R
import ir.groid.coinmaster.databinding.ItemMarketSmallBinding
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.util.Constans.BASE_URL_IMAG
import ir.groid.coinmaster.util.RecyclerEvent
import ir.groid.coinmaster.util.load


class MarketAdapter(
    private val event: RecyclerEvent<RCoinData>
) :
    RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {
    private lateinit var binding: ItemMarketSmallBinding
    private var listCoins: ArrayList<RCoinData> = arrayListOf()

    inner class MarketViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: RCoinData) {

            binding.apply {
                txtCoinName.text = data.txtCoinName

                txtPrice.text = data.txtPrice ?: "Null~> Test Mode"

                txtMarketCap.text = data.txtMarketCap ?: "Null~> Test Mode"

                txtTaghir.text = data.txtTaghir

                txtTaghir.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        setColor(data.txtTaghir!!.toDouble())
                    )
                )

                imgItem.load(BASE_URL_IMAG + data.img!!)
            }
            itemView.setOnClickListener { event.onClick(data) }
        }

        private val setColor: (i: Double) -> Int = {
            if (it > 0) {
                R.color.colorGain
            } else if (it < 0) {
                R.color.colorLoss
            } else {
                R.color.secondaryTextColor
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        binding = ItemMarketSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarketViewHolder()
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        holder.bind(listCoins[position])
    }

    override fun getItemCount(): Int = listCoins.size


    @SuppressLint("NotifyDataSetChanged")
    fun submit(new: List<RCoinData>) {
        listCoins.clear()
        listCoins.addAll(new)
        notifyDataSetChanged()
    }
}

