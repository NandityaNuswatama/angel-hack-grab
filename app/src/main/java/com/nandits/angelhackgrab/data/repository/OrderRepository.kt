package com.nandits.angelhackgrab.data.repository

import com.nandits.angelhackgrab.data.di.OrderService
import com.nandits.angelhackgrab.data.request.DriverRequest
import com.nandits.angelhackgrab.data.request.GenerateStickerRequest
import com.nandits.angelhackgrab.data.request.OrderRequest
import com.nandits.angelhackgrab.data.response.CreateOrderResponse
import com.nandits.angelhackgrab.data.response.DriverResponse
import com.nandits.angelhackgrab.data.response.GenerateStickerResponse
import com.nandits.angelhackgrab.data.response.Merchant
import com.nandits.angelhackgrab.data.response.Order
import com.nandits.angelhackgrab.data.response.OrderResponse
import javax.inject.Inject

class OrderRepository @Inject constructor(private val orderService: OrderService) {
    suspend fun getOrder(id: Int): OrderResponse {
        return orderService.getOrder(id)
    }

    suspend fun getMerchant(id: Int): Merchant {
        return orderService.getMerchant(id)
    }

    suspend fun createOrder(originalPrice: Int, totalPrice: Int, merchantId: Int, driverId: Int): CreateOrderResponse {
        return orderService.createOrder(OrderRequest(originalPrice, totalPrice, (50..80).random(), 2, merchantId, driverId))
    }

    suspend fun getDriver(id: Int): DriverResponse {
        return orderService.getDriverStory(DriverRequest(id))
    }

    suspend fun generateSticker(statement: String): GenerateStickerResponse {
        return orderService.generateSticker(GenerateStickerRequest(statement))
    }
}