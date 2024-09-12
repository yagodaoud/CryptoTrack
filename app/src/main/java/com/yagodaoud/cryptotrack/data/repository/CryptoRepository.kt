package com.yagodaoud.cryptotrack.data.repository

import com.yagodaoud.cryptotrack.data.api.CoinMarketCapAPI
import com.yagodaoud.cryptotrack.data.model.CryptoCurrency

class CryptoRepository(private val api: CoinMarketCapAPI) {

    suspend fun getCryptoPrices(apiKey: String, currency: String): List<CryptoCurrency>? {
        val response = api.getCryptoPrices(apiKey, currency)
        return if (response.isSuccessful) {
            response.body()?.cryptocurrencies
        } else {
            null
        }
    }
}
