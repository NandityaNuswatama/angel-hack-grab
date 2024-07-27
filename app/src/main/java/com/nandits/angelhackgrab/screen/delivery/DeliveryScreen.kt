package com.nandits.angelhackgrab.screen.delivery

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nandits.angelhackgrab.datamodel.StickerDataModel
import com.nandits.angelhackgrab.screen.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryScreen(
    orderId: Int,
    driverId: Int,
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val sheetState = rememberBottomSheetScaffoldState()
    val driver by viewModel.driver.collectAsState()
    val order by viewModel.order.collectAsState()
    val sticker by viewModel.sticker.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getOrder(orderId)
        viewModel.getDriver(driverId)
    }

    order?.let { orderDataModel ->
        driver?.let { driverDataModel ->
            DeliveryLayout(
                sheetState,
                orderDataModel = orderDataModel,
                driverDataModel = driverDataModel,
                stickerDataModel = StickerDataModel(),
                onCompleteOrderClicked = {

                },
                onGenerateSticker = {
                    viewModel.generateSticker(it)
                },
            )
        }
    }

    sticker?.let {
        DeliveryLayout(
            sheetState,
            orderDataModel = order!!,
            driverDataModel = driver!!,
            stickerDataModel = it,
            onCompleteOrderClicked = {

            },
            onGenerateSticker = { prompt ->
                viewModel.generateSticker(prompt)
            },
        )
    }
}