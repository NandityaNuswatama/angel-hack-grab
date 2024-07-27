package com.nandits.angelhackgrab.screen.merchant

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    var orderDataModel by remember { mutableStateOf(OrderDataModel()) }

    LaunchedEffect(key1 = Unit) {
        viewModel.getMerchant((1..3).random())
    }

    merchant?.let { merchantModel ->
        orderDataModel =
            OrderDataModel(itemsCount = 2, totalPrice = formatNumber(addTwoRandomItemsByIndex(merchantModel.productDataModels.map { it.numberPrice })))
        MerchantLayout(
            merchantDataModel = merchantModel,
            orderDataModel = orderDataModel,
            onBackClicked = {

            },
            onOrderClicked = {
//                navController.
            })
    }
}