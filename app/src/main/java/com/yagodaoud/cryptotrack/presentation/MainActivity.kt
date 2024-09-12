package com.yagodaoud.cryptotrack.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.wear.compose.material.Text
import com.yagodaoud.cryptotrack.data.api.CoinMarketCapAPI
import com.yagodaoud.cryptotrack.data.model.CryptoCurrency
import com.yagodaoud.cryptotrack.data.repository.CryptoRepository
import com.yagodaoud.cryptotrack.data.viewmodel.CryptoViewModel
import com.yagodaoud.cryptotrack.data.viewmodel.CryptoViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: CryptoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pro-api.coinmarketcap.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(CoinMarketCapAPI::class.java)

        val repository = CryptoRepository(api)
        val factory = CryptoViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CryptoViewModel::class.java)

        setContent {
            ObserveCryptoPrices(viewModel)
        }

        viewModel.fetchCryptoPrices("e77bacb5-8443-4bc7-8f5b-e0e26b497abd", "USD")
    }

    @Composable
    fun ObserveCryptoPrices(viewModel: CryptoViewModel) {
        val cryptoList by viewModel.cryptoList.collectAsState()

        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            LazyColumn {
                cryptoList?.let { list ->
                    items(list.size) { index ->
                        CryptoItem(list[index])
                    }
                }
            }
        }
    }

    @Composable
    fun CryptoItem(crypto: CryptoCurrency) {
        val price = crypto.quote["USD"]?.price ?: "N/A"

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.DarkGray)
                .padding(8.dp)
        ) {
            Text(
                text = "${crypto.name}: $price USD",
                color = Color.White,
                style = TextStyle(fontSize = 16.sp)
            )
        }
    }
}
