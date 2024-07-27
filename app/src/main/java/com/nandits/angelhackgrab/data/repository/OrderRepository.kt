package com.nandits.angelhackgrab.data.repository

import com.nandits.angelhackgrab.data.di.OrderService
import com.nandits.angelhackgrab.data.response.Merchant
import com.nandits.angelhackgrab.data.response.OrderResponse
import javax.inject.Inject

class OrderRepository @Inject constructor(private val orderService: OrderService) {
    suspend fun getOrders(): OrderResponse {
        return orderService.getOrders()
    }

    suspend fun getMerchant(id: Int): Merchant {
        return orderService.getMerchant(id)
    }
}