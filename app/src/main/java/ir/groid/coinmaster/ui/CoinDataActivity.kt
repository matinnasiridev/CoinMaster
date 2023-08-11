package ir.groid.coinmaster.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.groid.coinmaster.databinding.ActivityCoinDataBinding
import ir.groid.coinmaster.util.lunch
import ir.groid.coinmaster.viewModels.CoinDataVM
import org.koin.android.ext.android.inject


class CoinDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinDataBinding
    private val viewM: CoinDataVM by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCoinDataBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        about()
    }

    private fun about() {


        // Open Url!
        binding.layoutAbout.apply {
            txtWebsite.lunch("")
            txtTwitter.lunch("")
            txtRaddit.lunch("")
            txtGithub.lunch("")
        }
        // -->
    }
}