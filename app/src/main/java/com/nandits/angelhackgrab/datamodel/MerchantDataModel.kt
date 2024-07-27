package com.nandits.angelhackgrab.datamodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MerchantDataModel(
    var merchantId: String = "",
    var bannerImageUrl: String = "",
    var merchantImageUrl: String = "",
    var highlightedProducts: List<Product> = emptyList(),
    var products: List<Product> = emptyList(),
    var deliveryTime: String = "",
    var deliveryPrice: String = "",
) : Parcelable

@Parcelize
data class Product(
    var productId: String = "",
    var productImageUrl: String = "",
    var name: String = "",
    var description: String = "",
    var price: String = "",
    var originalPrice: String = "",
) : Parcelable
