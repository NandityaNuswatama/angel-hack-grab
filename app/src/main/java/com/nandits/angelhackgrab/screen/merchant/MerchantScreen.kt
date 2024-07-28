package com.nandits.angelhackgrab.screen.merchant

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nandits.angelhackgrab.data.bridge.addTwoRandomItemsByIndex
import com.nandits.angelhackgrab.data.bridge.formatNumber
import com.nandits.angelhackgrab.datamodel.OrderDataModel
import com.nandits.angelhackgrab.screen.OrderViewModel

@Composable
fun MerchantScreen(navController: NavController, viewModel: OrderViewModel = hiltViewModel()) {
    val merchant by viewModel.merchant.collectAsState()
    val orderCreated by viewModel.orderCreated.collectAsState()
    var orderDataModel by remember { mutableStateOf(OrderDataModel()) }
    var totalPrice by remember { mutableIntStateOf(0) }
    var originalPrice by remember { mutableIntStateOf(0) }
    var isOnDisplay by remember { mutableStateOf(true) }
    var isBlank by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit) {
        viewModel.getMerchant((1..3).random())
    }

    merchant?.let { merchantModel ->
        if (isBlank) {
            totalPrice = addTwoRandomItemsByIndex(merchantModel.productDataModels.map { it.numberPrice })
            originalPrice = addTwoRandomItemsByIndex(merchantModel.productDataModels.map { it.numberOriginalPrice })
            isBlank = false
        }
        orderDataModel =
            OrderDataModel(itemsCount = 2, totalPrice = formatNumber(totalPrice))
        MerchantLayout(
            merchantDataModel = merchantModel,
            orderDataModel = orderDataModel,
            onBackClicked = {},
            onOrderClicked = {
                viewModel.createOrder(totalPrice, originalPrice, merchantModel.merchantId)
            })
    }

    if (isOnDisplay) {
        orderCreated?.let {
            navController.popBackStack()
            navController.navigate("delivery/${it.second}")
            isOnDisplay = false
        }
    }
}