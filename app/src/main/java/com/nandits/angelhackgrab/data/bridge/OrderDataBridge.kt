package com.nandits.angelhackgrab.data.bridge

import com.nandits.angelhackgrab.data.response.DriverResponse
import com.nandits.angelhackgrab.data.response.Merchant
import com.nandits.angelhackgrab.data.response.Order
import com.nandits.angelhackgrab.data.response.Product
import com.nandits.angelhackgrab.datamodel.DriverDataModel
import com.nandits.angelhackgrab.datamodel.MerchantDataModel
import com.nandits.angelhackgrab.datamodel.OrderDataModel
import com.nandits.angelhackgrab.datamodel.ProductDataModel
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun convertMerchantToDataModel(merchant: Merchant): MerchantDataModel {
    return MerchantDataModel(
        merchantId = merchant.id,
        bannerImageUrl = merchant.banner_url,
        merchantImageUrl = merchant.image_url,
        productDataModels = merchant.products.map { convertProductToDataModel(it) },
        deliveryTime = addMinutesToCurrentTime(merchant.delivery_time),
        deliveryPrice = formatNumber(merchant.delivery_price),
        name = merchant.name,
        address = merchant.address,
        rating = merchant.rating.toString()
    )
}

fun convertProductToDataModel(product: Product): ProductDataModel {
    return ProductDataModel(
        productId = product.id,
        productImageUrl = product.image_url,
        name = product.name,
        price = formatNumber(product.price),
        originalPrice = formatNumber(product.original_price),
        numberPrice = product.price,
        numberOriginalPrice = product.original_price
    )
}

fun convertOrderToDataModel(order: Order): OrderDataModel {
    return OrderDataModel(
        arriveTime = addMinutesToCurrentTime(order.arrival_time),
        totalPrice = formatNumber(order.total_price),
        originalPrice = formatNumber(order.original_price),
        itemsCount = 2,
        pointsEarned = formatNumber(order.earned_point),
        merchantId = order.merchant_id
    )
}

fun convertDriverToDataModel(driver: DriverResponse): DriverDataModel {
    return DriverDataModel(
        driverId = driver.id,
        name = driver.name,
        rating = driver.rating,
        vehicle = driver.vehicle,
        vehicleNumber = driver.vehicle_number,
        story = driver.story,
        photoUrl = driver.image_url

    )
}

fun formatNumber(number: Int): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(number).replace(",", ".")
}

fun addMinutesToCurrentTime(minutes: Int): String {
    val now = LocalDateTime.now()
    val newTime = now.plusMinutes(minutes.toLong())
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return newTime.format(formatter)
}

fun addTwoRandomItemsByIndex(list: List<Int>): Int {
    if (list.size < 2) {
        throw IllegalArgumentException("List must have at least two elements")
    }

    val indices = list.indices.shuffled().take(2)
    return list[indices[0]] + list[indices[1]]
}
