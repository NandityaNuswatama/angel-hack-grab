package com.nandits.angelhackgrab.screen.merchant

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.nandits.angelhackgrab.datamodel.MerchantDataModel
import com.nandits.angelhackgrab.datamodel.OrderDataModel
import com.nandits.angelhackgrab.datamodel.Product
import com.nandits.angelhackgrab.screen.common.GrabImage

@Composable
fun MerchantLayout(
    merchantDataModel: MerchantDataModel,
    orderDataModel: OrderDataModel,
    onBackClicked: () -> Unit,
    onOrderClicked: () -> Unit
) {
    val rememberVerticalScroll = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        GrabImage(
            imageURL = merchantDataModel.bannerImageUrl, modifier = Modifier
                .height(260.dp)
                .fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberVerticalScroll)
                .padding(top = 56.dp)
        ) {
            Row {
                IconButton(
                    modifier = Modifier.padding(start = 2.dp),
                    onClick = onBackClicked,
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }

            Spacer(Modifier.height(24.dp))

            Card(
                modifier = Modifier,
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(Modifier.padding(12.dp)) {
                    GrabImage(imageURL = merchantDataModel.merchantImageUrl, modifier = Modifier.size(100.dp))

                    Spacer(Modifier.width(14.dp))

                    Column(Modifier.weight(1f)) {
                        Text(text = merchantDataModel.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = merchantDataModel.address, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }


        }

        Button(onClick = onOrderClicked) {
            Text(text = "Order ${orderDataModel.itemsCount} Items", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f) )
            Text(text = "Rp. ${orderDataModel.totalPrice} (Incl. tax)", style = MaterialTheme.typography.titleSmall )
        }
    }
}

@Composable
fun HighlightedProduct(product: Product) {
    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(Modifier.padding(12.dp)) {
            GrabImage(imageURL = product.productImageUrl, modifier = Modifier.size(100.dp))

            Spacer(Modifier.width(14.dp))

            Column(Modifier.weight(1f)) {
                Text(text = product.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = product.description, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
                Row {
                    Text(text = product.price, style = MaterialTheme.typography.bodyLarge)
                    Text(text = product.originalPrice, style = MaterialTheme.typography.bodyMedium, textDecoration = TextDecoration.LineThrough)
                }
            }
        }
    }
}