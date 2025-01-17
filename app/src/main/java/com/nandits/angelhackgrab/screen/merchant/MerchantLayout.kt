package com.nandits.angelhackgrab.screen.merchant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.nandits.angelhackgrab.R
import com.nandits.angelhackgrab.datamodel.MerchantDataModel
import com.nandits.angelhackgrab.datamodel.OrderDataModel
import com.nandits.angelhackgrab.datamodel.ProductDataModel
import com.nandits.angelhackgrab.screen.common.GrabImage

@Composable
fun MerchantLayout(
    merchantDataModel: MerchantDataModel,
    orderDataModel: OrderDataModel,
    onBackClicked: () -> Unit,
    onOrderClicked: () -> Unit
) {
    Box(modifier = Modifier
        .background(Color.LightGray)
        .fillMaxSize()) {
        GrabImage(
            imageURL = merchantDataModel.bannerImageUrl, modifier = Modifier
                .height(260.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, end = 16.dp, start = 16.dp)
        ) {
            IconButton(
                onClick = onBackClicked,
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }

            Spacer(Modifier.height(48.dp))

            Card(
                shape = RoundedCornerShape(12.dp),
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Row(Modifier.padding(12.dp)) {
                        GrabImage(imageURL = merchantDataModel.merchantImageUrl, modifier = Modifier.size(100.dp))

                        Spacer(Modifier.width(14.dp))

                        Column(verticalArrangement = Arrangement.Center) {
                            Text(text = merchantDataModel.name, style = MaterialTheme.typography.titleMedium)
                            Text(text = merchantDataModel.address, style = MaterialTheme.typography.bodyMedium)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                for (i in 1 until 5) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = Color(0XFFF8D368),
                                        modifier = Modifier
                                            .padding(end = 2.dp)
                                            .size(18.dp)
                                    )
                                }
                                Text(text = merchantDataModel.rating)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                GrabImage(
                                    imageURL = R.drawable.il_grab_delivery_icon,
                                    modifier = Modifier
                                        .height(20.dp)
                                        .width(28.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Rp. ${merchantDataModel.deliveryPrice}")
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(horizontal = 16.dp)
            ) {
                GrabImage(
                    imageURL = R.drawable.il_grab_merchant_product_sort, modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                Text(text = "For You", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(start = 4.dp))

                LazyColumn(modifier = Modifier
                    .height(500.dp)
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 56.dp)
                ) {
                    itemsIndexed(merchantDataModel.productDataModels) { index, _ ->
                        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                            if (index % 2 == 0) {
                                ProductItem(productDataModel = merchantDataModel.productDataModels[index])

                                if (index < merchantDataModel.productDataModels.size) {
                                    ProductItem(productDataModel = merchantDataModel.productDataModels[index + 1])
                                }
                            }
                        }

                        Spacer(modifier = Modifier.padding(bottom = 12.dp))
                    }
                }
            }
        }

        Button(
            onClick = onOrderClicked, modifier = Modifier
                .height(48.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.9f)
        ) {
            Text(text = "Order ${orderDataModel.itemsCount} Items", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
            Text(text = "Rp. ${orderDataModel.totalPrice} (Incl. tax)", style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Composable
fun ProductItem(productDataModel: ProductDataModel) {
    Card(
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Box(modifier = Modifier.size(160.dp)) {
                GrabImage(
                    imageURL = productDataModel.productImageUrl, modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                GrabImage(
                    imageURL = R.drawable.il_grab_fab_add, modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 16.dp, end = 16.dp)
                )
            }

            Spacer(Modifier.height(4.dp))

            Column(Modifier.width(150.dp)) {
                Text(text = productDataModel.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = "Rp. ${productDataModel.price}", style = MaterialTheme.typography.titleSmall)
                Text(text = "Rp. ${productDataModel.originalPrice}", style = MaterialTheme.typography.bodySmall, textDecoration = TextDecoration.LineThrough)
            }
        }
    }
}