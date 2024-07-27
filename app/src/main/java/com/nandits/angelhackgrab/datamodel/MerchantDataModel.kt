package com.nandits.angelhackgrab.datamodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MerchantDataModel(
    var merchantId: Int = 0,
    var bannerImageUrl: String = "",
    var merchantImageUrl: String = "",
    var productDataModels: List<ProductDataModel> = emptyList(),
    var deliveryTime: String = "",
    var deliveryPrice: String = "",
    var name: String = "",
    var address: String = "",
    var rating: String = ""
) : Parcelable

@Parcelize
data class ProductDataModel(
    var productId: Int = 0,
    var productImageUrl: String = "",
    var name: String = "",
    var price: String = "",
    var originalPrice: String = "",
    var numberPrice: Int = 0
) : Parcelable
