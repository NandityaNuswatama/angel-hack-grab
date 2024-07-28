package com.nandits.angelhackgrab.data.response

data class OrderResponse(
    val order: Order
)

data class Order(
    val id: Int,
    val original_price: Int,
    val total_price: Int,
    val earned_point: Int,
    val item_count: Int,
    val merchant_id: Int,
    val driver_id: Int,
    val arrival_time: Int
)