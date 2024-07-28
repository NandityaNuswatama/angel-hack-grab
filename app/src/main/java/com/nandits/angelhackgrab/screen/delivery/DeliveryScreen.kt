package com.nandits.angelhackgrab.screen.delivery

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nandits.angelhackgrab.datamodel.DriverDataModel
import com.nandits.angelhackgrab.datamodel.StickerDataModel
import com.nandits.angelhackgrab.screen.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryScreen(
    driverId: Int,
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val sheetState = rememberBottomSheetScaffoldState()
    val driver by viewModel.driver.collectAsState()
    val sticker by viewModel.sticker.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getDriver(driverId)
    }

    driver?.let {
        DeliveryLayout(
            sheetState,
            orderDataModel = viewModel.getLocalOrder(),
            driverDataModel = it,
            stickerDataModel = sticker ?: StickerDataModel(),
            onCompleteOrderClicked = {
                viewModel.savaToLocal()
                navController.navigate("rating")
            },
            onGenerateSticker = {
                viewModel.generateSticker(it)
            },
        )
    }

    sticker?.let {
        DeliveryLayout(
            sheetState,
            orderDataModel = viewModel.getLocalOrder(),
            driverDataModel = driver ?: DriverDataModel(),
            stickerDataModel = it,
            onCompleteOrderClicked = {
                viewModel.savaToLocal()
                navController.navigate("rating")
            },
            onGenerateSticker = { prompt ->
                viewModel.generateSticker(prompt)
            },
        )
    }
}