package com.yagodaoud.cryptotrack.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yagodaoud.cryptotrack.data.model.CryptoCurrency
import com.yagodaoud.cryptotrack.data.repository.CryptoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CryptoViewModel(private val repository: CryptoRepository) : ViewModel() {
    private val _cryptoList = MutableStateFlow<List<CryptoCurrency>?>(null)
    val cryptoList: StateFlow<List<CryptoCurrency>?> = _cryptoList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchCryptoPrices(apiKey: String, currency: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val data = repository.getCryptoPrices(apiKey, currency)
                _cryptoList.value = data
            } finally {
                _isLoading.value = false
            }
        }
    }
}
