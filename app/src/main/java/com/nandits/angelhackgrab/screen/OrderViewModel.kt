package com.nandits.angelhackgrab.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandits.angelhackgrab.data.bridge.convertMerchantToDataModel
import com.nandits.angelhackgrab.data.repository.OrderRepository
import com.nandits.angelhackgrab.data.response.OrderResponse
import com.nandits.angelhackgrab.datamodel.MerchantDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val repository: OrderRepository) : ViewModel() {
    private val _orders = MutableStateFlow<OrderResponse?>(null)
    val orders: StateFlow<OrderResponse?> get() = _orders.asStateFlow()

    private val _merchant = MutableStateFlow<MerchantDataModel?>(null)
    val merchant: StateFlow<MerchantDataModel?> get() = _merchant.asStateFlow()

    init {

    }

    fun getOrders() {
        viewModelScope.launch {
            try {
                _orders.value = repository.getOrders()
            } catch (_: Exception) {

            }
        }
    }

    fun getMerchant(id: Int) {
        try {
            viewModelScope.launch {
                val merchant = repository.getMerchant(id)
                _merchant.value = convertMerchantToDataModel(merchant)
            }
        } catch (e: Exception) {
            Log.e("GGG", "Error", e)
        }
    }
}