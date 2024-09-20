package com.yagodaoud.cryptotrack.data.model

import com.google.gson.annotations.SerializedName

data class CryptoResponse(
    @SerializedName("data")
    val cryptocurrencies: List<CryptoCurrency>,
    @SerializedName("status")
    val status: Status
)

data class Status(
    val timestamp: String,
    val errorCode: Int,
    val errorMessage: String?
)


data class BitcoinResponse(
    @SerializedName("data")
    val data: BitcoinData
)

data class BitcoinData(
    @SerializedName("BTC")
    val bitcoin: Bitcoin
)

data class Bitcoin(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("quote")
    val quote: BitcoinQuote
)

data class BitcoinQuote(
    @SerializedName("USD")
    val usd: Quote
)

data class QuoteBtc(
    @SerializedName("price")
    val price: Double,
    @SerializedName("volume_24h")
    val volume24h: Double,
    @SerializedName("percent_change_1h")
    val percentChange1h: Double,
    @SerializedName("percent_change_24h")
    val percentChange24h: Double
)