package com.nandits.angelhackgrab.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandits.angelhackgrab.data.bridge.convertDriverToDataModel
import com.nandits.angelhackgrab.data.bridge.convertMerchantToDataModel
import com.nandits.angelhackgrab.data.bridge.convertOrderToDataModel
import com.nandits.angelhackgrab.data.repository.OrderRepository
import com.nandits.angelhackgrab.datamodel.DriverDataModel
import com.nandits.angelhackgrab.datamodel.MerchantDataModel
import com.nandits.angelhackgrab.datamodel.OrderDataModel
import com.nandits.angelhackgrab.datamodel.StickerDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val repository: OrderRepository) : ViewModel() {
    private val _order = MutableStateFlow<OrderDataModel?>(null)
    val order: StateFlow<OrderDataModel?> get() = _order.asStateFlow()

    private val _merchant = MutableStateFlow<MerchantDataModel?>(null)
    val merchant: StateFlow<MerchantDataModel?> get() = _merchant.asStateFlow()

    private val _driver = MutableStateFlow<DriverDataModel?>(null)
    val driver: StateFlow<DriverDataModel?> get() = _driver.asStateFlow()

    private val _sticker = MutableStateFlow<StickerDataModel?>(null)
    val sticker: StateFlow<StickerDataModel?> get() = _sticker.asStateFlow()

    private val _orderCreated = MutableStateFlow<Pair<Int, Int>?>(null)
    val orderCreated: StateFlow<Pair<Int, Int>?> get() = _orderCreated.asStateFlow()

    fun createOrder(totalPrice: Int, originalPrice: Int, merchantId: Int) {
        viewModelScope.launch {
            val driverId = (1..3).random()
            val response = repository.createOrder(totalPrice, originalPrice, merchantId, driverId)
            _orderCreated.value = Pair(response.id, driverId)
        }
    }

    fun getDriver(id: Int) {
        viewModelScope.launch {
            val response = repository.getDriver(id)
            _driver.value = convertDriverToDataModel(response)
        }
    }

    fun generateSticker(prompt: String) {
        viewModelScope.launch {
            val response = repository.generateSticker(prompt).image_url
            _sticker.value = StickerDataModel(prompt, response)
        }
    }

    fun getOrder(id: Int) {
        viewModelScope.launch {
            val response = repository.getOrder(id)
            _order.value = convertOrderToDataModel(response)
        }
    }

    fun getMerchant(id: Int) {
        viewModelScope.launch {
            val merchant = repository.getMerchant(id)
            _merchant.value = convertMerchantToDataModel(merchant)
        }
    }
}