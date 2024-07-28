package com.nandits.angelhackgrab.screen

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nandits.angelhackgrab.data.bridge.convertDriverToDataModel
import com.nandits.angelhackgrab.data.bridge.convertMerchantToDataModel
import com.nandits.angelhackgrab.data.bridge.convertOrderToDataModel
import com.nandits.angelhackgrab.data.repository.OrderRepository
import com.nandits.angelhackgrab.datamodel.DriverDataModel
import com.nandits.angelhackgrab.datamodel.MerchantDataModel
import com.nandits.angelhackgrab.datamodel.OrderDataModel
import com.nandits.angelhackgrab.datamodel.StickerDataModel
import com.nandits.angelhackgrab.datamodel.constant.DRIVER
import com.nandits.angelhackgrab.datamodel.constant.MERCHANT
import com.nandits.angelhackgrab.datamodel.constant.ORDER
import com.nandits.angelhackgrab.datamodel.constant.STICKER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val repository: OrderRepository, private val sharedPref: SharedPreferences) : ViewModel() {
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
            getOrder(response.id)
            delay(300)
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

    private fun getOrder(id: Int) {
        viewModelScope.launch {
            val response = repository.getOrder(id)
            _order.value = convertOrderToDataModel(response.order)
            savaToLocal()
        }
    }

    fun getMerchant(id: Int) {
        clearLocal()
        viewModelScope.launch {
            val merchant = repository.getMerchant(id)
            _merchant.value = convertMerchantToDataModel(merchant)
        }
    }

    private fun saveToPrefs(key: String, value: String) {
        sharedPref.edit().putString(key, value).apply()
    }

    fun savaToLocal() {
        val gson = Gson()
        order.value?.let {
            saveToPrefs(ORDER, gson.toJson(it))
        }
        merchant.value?.let {
            saveToPrefs(MERCHANT, gson.toJson(it))
        }
        driver.value?.let {
            saveToPrefs(DRIVER, gson.toJson(it))
        }
        sticker.value?.let {
            saveToPrefs(STICKER, gson.toJson(it))
        }
    }

    fun getLocalOrder(): OrderDataModel {
        return convertFromJson<OrderDataModel>(sharedPref.getString(ORDER, "").orEmpty())
    }

    fun getLocalDriver(): DriverDataModel {
        return convertFromJson<DriverDataModel>(sharedPref.getString(DRIVER, "").orEmpty())
    }

    fun getLocalMerchant(): MerchantDataModel {
        return convertFromJson<MerchantDataModel>(sharedPref.getString(MERCHANT, "").orEmpty())
    }

    fun getLocalSticker(): StickerDataModel {
        val sticker = sharedPref.getString(STICKER, "")
        return if (sticker.orEmpty().isEmpty()) StickerDataModel() else convertFromJson<StickerDataModel>(sticker.orEmpty())
    }

    private fun clearLocal() {
        sharedPref.edit().clear().apply()
    }

    private inline fun <reified T> convertFromJson(json: String): T {
        val gson = Gson()
        return gson.fromJson(json, object : TypeToken<T>() {}.type)
    }
}