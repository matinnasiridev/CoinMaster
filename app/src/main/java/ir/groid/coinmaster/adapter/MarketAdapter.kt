package ir.groid.coinmaster.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.groid.coinmaster.apiManager.BASE_URL_IMAG
import ir.groid.coinmaster.R
import ir.groid.coinmaster.apiManager.model.CoinsData
import ir.groid.coinmaster.databinding.ItemMarketSmallBinding


class MarketAdapter(private val data: List<CoinsData.Data>, private val apiCallback: RecCallBack) :
    RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {
    private lateinit var binding: ItemMarketSmallBinding

    inner class MarketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun binds(data: CoinsData.Data) {

            binding.txtCoinName.text = data.coinInfo.fullName
            binding.txtPrice.text = data.dISPLAY.uSD.pRICE
            binding.txtMarketCap.text = data.rAW.uSD.mARKET

            val taghir = data.rAW.uSD.cHANGEPCT24HOUR
            if (taghir > 0) {
                binding.txtTaghir.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.colorGain
                    )
                )
                binding.txtTaghir.text =
                    data.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 4) + "%"
            } else if (taghir < 0) {
                binding.txtTaghir.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.colorLoss
                    )
                )
                binding.txtTaghir.text =
                    data.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 5) + "%"
            } else {
                binding.txtTaghir.text = "0%"
            }

            val marketCap = data.rAW.uSD.mKTCAP / 1000000000
            val indexDot = marketCap.toString().indexOf('.')
            binding.txtMarketCap.text = "$" + marketCap.toString().substring(0, indexDot + 3) + " B"


            Glide
                .with(itemView)
                .load(BASE_URL_IMAG + data.coinInfo.imageUrl)
                .into(binding.imgItem)


            itemView.setOnClickListener {
                apiCallback.onToch(data)
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

    interface RecCallBack {
        fun onToch(data: CoinsData.Data)
    }

}

