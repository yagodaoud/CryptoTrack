package com.yagodaoud.cryptotrack.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yagodaoud.cryptotrack.data.model.CryptoCurrency
import com.yagodaoud.cryptotrack.data.repository.CryptoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant

class CryptoViewModel(private val repository: CryptoRepository) : ViewModel() {
    private val _cryptoList = MutableStateFlow<List<CryptoCurrency>?>(null)
    val cryptoList: StateFlow<List<CryptoCurrency>?> = _cryptoList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var lastFetchTime: Instant? = null
    private val cacheDuration = 2 * 60

    fun fetchCryptoPrices(apiKey: String, currency: String) {
        _isLoading.value = true

        if (isCacheValid()) {
            viewModelScope.launch {
                delay(200)
                _isLoading.value = false
            }
            return
        }

        viewModelScope.launch {
            try {
                val data = repository.getCryptoPrices(apiKey, currency)
                _cryptoList.value = data
                lastFetchTime = Instant.now()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun isCacheValid(): Boolean {
        val now = Instant.now()
        return lastFetchTime?.let {
            now.epochSecond - it.epochSecond < cacheDuration
        } ?: false
    }
}
