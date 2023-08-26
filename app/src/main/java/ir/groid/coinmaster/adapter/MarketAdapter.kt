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
import ir.groid.coinmaster.di.AppService
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.util.Constans.BASE_URL_IMAG
import ir.groid.coinmaster.util.RecyclerEvent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class MarketAdapter(
    private val event: RecyclerEvent<RCoinData>
) :
    RecyclerView.Adapter<MarketAdapter.MarketViewHolder>(), KoinComponent {
    private lateinit var binding: ItemMarketNormalBinding
    private lateinit var binding2: ItemMarketShimmerBinding
    private val imageLoader by inject<AppService.ImageLoader>()
    private var listCoins: ArrayList<RCoinData> = arrayListOf()
    private var viewT: Boolean = false // true == Normal View

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

                imageLoader.loader(BASE_URL_IMAG + data.img, imgItem)
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
        if (viewT)
            holder.bind(listCoins[position])
    }

    override fun getItemViewType(position: Int): Int = if (viewT) 1 else 2

    override fun getItemCount(): Int = if (!viewT) 10 else listCoins.size


    @SuppressLint("NotifyDataSetChanged")
    fun submit(new: List<RCoinData>) {
        listCoins.clear()
        listCoins.addAll(new)
        notifyDataSetChanged()
    }

    /**
     * true = Normal View
     */
    @SuppressLint("NotifyDataSetChanged")
    fun switchToNormalView(ch: Boolean) {
        viewT = ch
        notifyDataSetChanged()
    }
}