package com.nandits.angelhackgrab.datamodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DriverDataModel(
    var driverId: Int = 0,
    var name: String = "",
    var rating: Double = 5.0,
    var vehicle: String = "",
    var vehicleNumber: String = "",
    var story: String = "",
    var photoUrl: String = ""
): Parcelable
