package com.nandits.angelhackgrab.datamodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderDataModel(
    var arriveTime: String = "",
    var totalPrice: String = "",
    var originalPrice: String = "",
    var itemsCount: Int = 0,
    var pointsEarned: String = "",
    var merchantId: Int = 0,
) : Parcelable

@Parcelize
data class Tip(
    var amount: String = "",
    var isSelected: Boolean = false
) : Parcelable

@Parcelize
data class QuickRating(
    var text: String = "",
    var isSelected: Boolean = false
) : Parcelable
