package com.yagodaoud.cryptotrack.data.repository

import android.util.Log
import com.yagodaoud.cryptotrack.data.api.CoinMarketCapAPI
import com.yagodaoud.cryptotrack.data.model.CryptoCurrency
import com.yagodaoud.cryptotrack.data.model.CryptoResponse
import retrofit2.Call
import retrofit2.Response

class CryptoRepository(private val api: CoinMarketCapAPI) {

    suspend fun getCryptoPrices(apiKey: String, currency: String): List<CryptoCurrency>? {
        val response = api.getCryptoPrices(apiKey, currency)
        return if (response.isSuccessful) {
            response.body()?.cryptocurrencies
        } else {
            null
        }
    }

    suspend fun getBtcPrice(apiKey: String, symbol: String, currency: String): Double? {
        return try {
            val response = api.getBtcPrice(apiKey, symbol, currency)
            if (response.isSuccessful) {
                val bitcoin = response.body()?.data?.bitcoin
                bitcoin?.quote?.usd?.price
            } else {
                Log.e("API Error", "Response unsuccessful: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("API Error", "Exception: ${e.message}")
            null
        }
    }
}
