package com.nandits.angelhackgrab.data.di

import com.nandits.angelhackgrab.data.request.DriverRequest
import com.nandits.angelhackgrab.data.request.GenerateStickerRequest
import com.nandits.angelhackgrab.data.request.OrderRequest
import com.nandits.angelhackgrab.data.response.CreateOrderResponse
import com.nandits.angelhackgrab.data.response.DriverResponse
import com.nandits.angelhackgrab.data.response.GenerateStickerResponse
import com.nandits.angelhackgrab.data.response.Merchant
import com.nandits.angelhackgrab.data.response.Order
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {
    @GET("orders/{id}")
    suspend fun getOrder(@Path("id") id: Int): Order

    @POST("orders")
    suspend fun createOrder(@Body orderRequest: OrderRequest): CreateOrderResponse

    @GET("merchants/{id}")
    suspend fun getMerchant(@Path("id") id: Int): Merchant

    @POST("generate-text")
    suspend fun getDriverStory(@Body driverRequest: DriverRequest): DriverResponse

    @POST("sentiment-statement")
    suspend fun generateSticker(@Body generateStickerRequest: GenerateStickerRequest): GenerateStickerResponse
}