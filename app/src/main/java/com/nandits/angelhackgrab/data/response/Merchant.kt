package com.nandits.angelhackgrab.data.response

data class Merchant(
    val id: Int,
    val name: String,
    val rating: Double,
    val address: String,
    val image_url: String,
    val banner_url: String,
    val delivery_time: Int,
    val delivery_price: Int,
    val products: List<Product>
)

data class Product(
    val id: Int,
    val name: String,
    val original_price: Int,
    val price: Int,
    val image_url: String
)
