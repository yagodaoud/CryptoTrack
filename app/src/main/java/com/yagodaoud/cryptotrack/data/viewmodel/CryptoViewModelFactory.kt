package com.yagodaoud.cryptotrack.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yagodaoud.cryptotrack.data.repository.CryptoRepository

class CryptoViewModelFactory(private val repository: CryptoRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CryptoViewModel::class.java) -> CryptoViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
