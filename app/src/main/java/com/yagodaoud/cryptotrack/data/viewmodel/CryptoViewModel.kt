package com.yagodaoud.cryptotrack.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yagodaoud.cryptotrack.data.model.CryptoCurrency
import com.yagodaoud.cryptotrack.data.repository.CryptoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CryptoViewModel(private val repository: CryptoRepository) : ViewModel() {

    val cryptoList = MutableStateFlow<List<CryptoCurrency>>(emptyList())

    fun fetchCryptoPrices(apiKey: String, currency: String) {
        viewModelScope.launch {
            val prices = repository.getCryptoPrices(apiKey, currency)
            if (prices != null) {
                cryptoList.value = prices
            }
        }
    }
}