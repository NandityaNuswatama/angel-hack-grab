package com.nandits.angelhackgrab.data.response

data class DriverResponse(
    val id: Int,
    val name: String,
    val vehicle: String,
    val vehicle_number: String,
    val image_url: String,
    val story: String,
    val rating: Double,
)