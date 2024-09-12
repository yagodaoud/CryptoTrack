package com.yagodaoud.cryptotrack.data.api

import com.yagodaoud.cryptotrack.data.model.CryptoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinMarketCapAPI {
    @GET("v1/cryptocurrency/listings/latest")
    suspend fun getCryptoPrices(
        @Query("CMC_PRO_API_KEY") apiKey: String,
        @Query("convert") currency: String
    ): Response<CryptoResponse>
}