package com.nandits.angelhackgrab.datamodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StickerDataModel(
    var prompt: String = "",
    var imageUrl: String = "",
) : Parcelable
