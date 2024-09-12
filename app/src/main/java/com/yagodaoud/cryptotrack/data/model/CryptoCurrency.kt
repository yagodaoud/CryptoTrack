package com.yagodaoud.cryptotrack.data.model

import com.google.gson.annotations.SerializedName

data class CryptoCurrency(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("quote")
    val quote: Map<String, Quote>
)
