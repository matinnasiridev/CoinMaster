package ir.groid.coinmaster.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ir.groid.coinmaster.R
import ir.groid.coinmaster.databinding.ItemMarketNormalBinding
import ir.groid.coinmaster.databinding.ItemMarketShimmerBinding
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.util.Constans.BASE_URL_IMAG
import ir.groid.coinmaster.util.RecyclerEvent
import ir.groid.coinmaster.util.load


class MarketAdapter(
    private val event: RecyclerEvent<RCoinData>
) :
    RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {
    private lateinit var binding: ItemMarketNormalBinding
    private lateinit var binding2: ItemMarketShimmerBinding
    private var listCoins: ArrayList<RCoinData> = arrayListOf()
    private var viewT: Int = 1

    inner class MarketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
            binding.root.setOnClickListener { event.onClick(data) }
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
        val bindShimmerMode: () -> Unit = { }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        binding = ItemMarketNormalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding2 = ItemMarketShimmerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MarketViewHolder(if (viewType == 1) binding.root else binding2.root)
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        if (viewT == 1)
            holder.bind(listCoins[position])
        else
            holder.bindShimmerMode
    }

    override fun getItemViewType(position: Int): Int {
        return if (listCoins.size == 0) {
            viewT = 2
            2
        } else {
            viewT = 1
            1
        }
    }

    override fun getItemCount(): Int = if (listCoins.size == 0) 10 else listCoins.size


    @SuppressLint("NotifyDataSetChanged")
    fun submit(new: List<RCoinData>) {
        listCoins.clear()
        listCoins.addAll(new)
        notifyDataSetChanged()
    }
}

