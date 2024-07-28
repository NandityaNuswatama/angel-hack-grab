package com.nandits.angelhackgrab.screen.rating

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.nandits.angelhackgrab.screen.OrderViewModel

@Composable
fun RatingScreen(
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel(),
) {

    RatingLayout(
        driverDataModel = viewModel.getLocalDriver(),
        orderDataModel = viewModel.getLocalOrder(),
        initialStickerDataModel = viewModel.getLocalSticker(),
        merchantDataModel = viewModel.getLocalMerchant(),
    ) {
        navController.navigate("main") {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
        }
    }
}