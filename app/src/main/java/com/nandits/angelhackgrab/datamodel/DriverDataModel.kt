package com.nandits.angelhackgrab.datamodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DriverDataModel(
    var driverId: String = "",
    var name: String = "",
    var rating: Double = 5.0,
    var vehicle: String = "",
    var vehicleNumber: String = "",
    var story: String = "",
): Parcelable
