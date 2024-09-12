package com.yagodaoud.cryptotrack.data.model

import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("price")
    val price: Double
)