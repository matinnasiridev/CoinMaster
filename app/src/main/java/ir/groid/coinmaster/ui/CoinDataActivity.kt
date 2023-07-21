package ir.groid.coinmaster.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.groid.coinmaster.databinding.ActivityCoinDataBinding


class CoinDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCoinDataBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}