package com.nandits.angelhackgrab.data.di

import com.nandits.angelhackgrab.data.response.Merchant
import com.nandits.angelhackgrab.data.response.OrderResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface OrderService {
    @GET("orders")
    suspend fun getOrders(): OrderResponse

    @GET("orders")
    suspend fun getOrder(): OrderResponse

    @GET("merchants/{id}")
    suspend fun getMerchant(@Path("id") id: Int): Merchant
}