package com.nandits.angelhackgrab.data.request

data class OrderRequest(
    val original_price: Int,
    val total_price: Int,
    val earned_point: Int,
    val item_count: Int,
    val merchant_id: Int,
    val driver_id: Int
)