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
