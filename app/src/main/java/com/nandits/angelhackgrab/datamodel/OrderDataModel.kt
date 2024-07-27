package com.nandits.angelhackgrab.datamodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderDataModel(
    var arriveTime: String = "",
    var merchantName: String = "",
    var totalPrice: String = "",
    var originalPrice: String = "",
    var itemsCount: Int = 0,
    var driver: DriverDataModel = DriverDataModel(),
    var tips: List<Tip> = emptyList(),
    var pointsEarned: String = "",
    var quickRatings: List<QuickRating> = emptyList(),
) :Parcelable

@Parcelize
data class Tip (
    var amount: Int = 0
): Parcelable

@Parcelize
data class QuickRating (
    var text: String = ""
): Parcelable
