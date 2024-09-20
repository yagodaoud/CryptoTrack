package com.yagodaoud.cryptotrack.data.complication

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.wear.complications.ComplicationProviderService
import androidx.wear.complications.data.ComplicationData
import androidx.wear.complications.data.ShortTextComplicationData
import androidx.wear.complications.data.PlainComplicationText
import androidx.wear.complications.data.ComplicationType
import androidx.wear.complications.ComplicationRequest
import androidx.wear.complications.data.MonochromaticImage
import com.yagodaoud.cryptotrack.R
import com.yagodaoud.cryptotrack.data.api.CoinMarketCapAPI
import com.yagodaoud.cryptotrack.data.repository.CryptoRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DecimalFormat

//import androidx.wear.complications.ComplicationManager

class CryptoComplicationService : ComplicationProviderService() {

    override fun getPreviewData(type: ComplicationType): ComplicationData? {
        return when (type) {
            ComplicationType.SHORT_TEXT -> ShortTextComplicationData.Builder(
                PlainComplicationText.Builder("$50,000").build(),
                PlainComplicationText.Builder("Preview").build()
            ).build()

            else -> null
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("RestrictedApi")
    override fun onComplicationRequest(
        request: ComplicationRequest,
        listener: ComplicationRequestListener
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val price = try {
                fetchBtcPrice()
            } catch (e: Exception) {
                null
            }

            val formattedPrice = price?.let { DecimalFormat("#.##").format(it) } ?: "Unavailable"

            if (request.complicationType == ComplicationType.SHORT_TEXT) {
                val complicationData = ShortTextComplicationData.Builder(
                    PlainComplicationText.Builder(formattedPrice).build(),
                    PlainComplicationText.Builder("BTC").build()
                )
                    .setMonochromaticImage(
                        MonochromaticImage.Builder(
                            Icon.createWithResource(
                                this@CryptoComplicationService,
                                R.drawable.ic_btc_monochrome
                            )
                        ).build()
                    )
                    .build()

                listener.onComplicationData(complicationData)
            } else {
                listener.onComplicationData(null)
            }
        }
    }

    private suspend fun fetchBtcPrice(): Double? {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pro-api.coinmarketcap.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(CoinMarketCapAPI::class.java)
        val repository = CryptoRepository(api)


        val response = repository.getBtcPrice("e77bacb5-8443-4bc7-8f5b-e0e26b497abd", "BTC", "USD")
        return response;
    }
}
