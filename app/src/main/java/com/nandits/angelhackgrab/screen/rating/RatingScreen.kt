package com.nandits.angelhackgrab.screen.rating

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nandits.angelhackgrab.datamodel.DriverDataModel
import com.nandits.angelhackgrab.datamodel.OrderDataModel
import com.nandits.angelhackgrab.datamodel.StickerDataModel
import com.nandits.angelhackgrab.screen.OrderViewModel

@Composable
fun RatingScreen(
    driverPhotoUrl: String,
    driverId: Int,
    stickerUrl: String,
    totalPrice: String,
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel()
) {
    RatingLayout(
        orderDataModel = OrderDataModel(totalPrice = totalPrice),
        driverDataModel = DriverDataModel(photoUrl = driverPhotoUrl, driverId = driverId),
        initialStickerDataModel = StickerDataModel(imageUrl = stickerUrl)
    )
}