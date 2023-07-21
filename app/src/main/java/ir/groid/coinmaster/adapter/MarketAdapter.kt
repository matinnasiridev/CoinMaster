package ir.groid.coinmaster.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.groid.coinmaster.R
import ir.groid.coinmaster.responce.CoinsData
import ir.groid.coinmaster.databinding.ItemMarketSmallBinding
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.util.Constans.BASE_URL_IMAG


class MarketAdapter(
    private val data: List<RCoinData>,
    private val apiCallback: RecCallBack<RCoinData>
) :
    RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {
    private lateinit var binding: ItemMarketSmallBinding

    inner class MarketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun binds(data: RCoinData) {

            binding.txtCoinName.text = data.txtCoinName
            binding.txtPrice.text = data.txtPrice
            binding.txtMarketCap.text = data.txtMarketCap

            val taghir = data.txtTaghir
            if (taghir!! > 0) {
                binding.txtTaghir.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.colorGain
                    )
                )
                binding.txtTaghir.text =
                    data.txtTaghir.toString().substring(0, 4) + "%"
            } else if (taghir < 0) {
                binding.txtTaghir.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.colorLoss
                    )
                )
                binding.txtTaghir.text =
                    data.txtTaghir.toString().substring(0, 5) + "%"
            } else {
                binding.txtTaghir.text = "0%"
            }

            // TODO Fix This
            val marketCap = data.txtTaghir / 1000000000
            val indexDot = marketCap.toString().indexOf('.')
            binding.txtMarketCap.text = "$" + marketCap.toString().substring(0, indexDot + 3) + " B"


            Glide
                .with(itemView)
                .load(BASE_URL_IMAG + data.img)
                .into(binding.imgItem)


            itemView.setOnClickListener {
                apiCallback.onTouch(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        binding = ItemMarketSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarketViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        holder.binds(data[position])
    }

    override fun getItemCount(): Int = data.size

    interface RecCallBack<T> {
        fun onTouch(data: T)
    }

}

