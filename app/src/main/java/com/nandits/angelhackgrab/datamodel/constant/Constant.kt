package com.nandits.angelhackgrab.datamodel.constant

import com.nandits.angelhackgrab.datamodel.RatingDataModel
import com.nandits.angelhackgrab.datamodel.Tip

fun getTips(): List<Tip> {
    return listOf(
        Tip("Rp. 2.000", false),
        Tip("Rp. 5.000", false),
        Tip("Rp. 7.000", false),
        Tip("Rp. 10.000", false),
        Tip("Rp. 20.000", false),
        Tip("Rp. 50.000", false),
    )
}

fun getRatings(): List<RatingDataModel> {
    return listOf(
        RatingDataModel(1, "Very Poor"),
        RatingDataModel(2, "Poor"),
        RatingDataModel(3, "Good"),
        RatingDataModel(4, "Very Good"),
        RatingDataModel(5, "Perfect"),
    )
}

fun getQuickRatings(): List<String> {
    return listOf("Friendly", "Contactless Delivery", "Punctually", "Service", "Followed Instructions", "Attitude", "Hygenic", "Dressed in Grab")
}