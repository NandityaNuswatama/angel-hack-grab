package com.nandits.angelhackgrab.screen.rating

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nandits.angelhackgrab.datamodel.DriverDataModel
import com.nandits.angelhackgrab.datamodel.OrderDataModel
import com.nandits.angelhackgrab.datamodel.StickerDataModel
import com.nandits.angelhackgrab.datamodel.constant.getQuickRatings
import com.nandits.angelhackgrab.datamodel.constant.getRatings
import com.nandits.angelhackgrab.screen.common.GrabRoundImage
import com.nandits.angelhackgrab.screen.delivery.CardDriverGenerateSticker
import com.nandits.angelhackgrab.screen.delivery.DetailStickerBottomSheet
import com.nandits.angelhackgrab.ui.theme.AngelHackGrabTheme
import com.nandits.angelhackgrab.ui.theme.GrabOnSecondary
import com.nandits.angelhackgrab.ui.theme.GrabPrimary
import com.nandits.angelhackgrab.ui.theme.GrabPrimaryVariant
import com.nandits.angelhackgrab.ui.theme.GrabPrimaryVariant15

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingScreen(driverDataModel: DriverDataModel, orderDataModel: OrderDataModel, initialStickerDataModel: StickerDataModel) {
    var initialRating by remember { mutableStateOf(0) }
    var lastRating by remember { mutableStateOf(0) }
    var showBottomSheetDetailSticker by remember { mutableStateOf(false) }
    var stickerDataModel by remember { mutableStateOf(initialStickerDataModel) }

    Column {
        Row(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
            if (initialRating != 0) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = Color.Black, modifier = Modifier.clickable { initialRating = 0 })
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Contact Support", textAlign = TextAlign.End, color = Color.LightGray)
        }

        Spacer(modifier = Modifier.size(24.dp))

        if (initialRating == 0) {
            PreRatingLayout(driverDataModel = driverDataModel, orderDataModel = orderDataModel, prevRating = lastRating) {
                lastRating = it
                initialRating = it
            }
        } else {
            RatingLayout(
                driverDataModel,
                orderDataModel,
                initialRating,
                onSelectRating = { lastRating = it },
                onGenerateSticker = { showBottomSheetDetailSticker = true },
                onSubmit = {})
        }

        if (showBottomSheetDetailSticker) {
            ModalBottomSheet(
                dragHandle = null,
                onDismissRequest = { showBottomSheetDetailSticker = false },
                sheetState = rememberStandardBottomSheetState(confirmValueChange = { false }, skipHiddenState = false)
            ) {
                DetailStickerBottomSheet(
                    stickerDataModel = stickerDataModel,
                    onRegenerateClicked = {
                        showBottomSheetDetailSticker = false
                    }, onUseClicked = {
                        showBottomSheetDetailSticker = false
                    }
                )
            }
        }
    }
}

@Composable
fun PreRatingLayout(driverDataModel: DriverDataModel, orderDataModel: OrderDataModel, prevRating: Int = 0, onRatingFilled: (Int) -> Unit) {
    var rating by remember { mutableStateOf(prevRating) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {

        GrabRoundImage(imageURL = driverDataModel.photoUrl, modifier = Modifier.size(90.dp))

        Spacer(modifier = Modifier.size(24.dp))

        Text(
            text = "Let’s rate your driver’s\ndelivery service",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = GrabOnSecondary,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "How was the delivery of your order\nfrom Nanditya bakso - cipete?",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.LightGray,
            style = MaterialTheme.typography.bodyMedium
        )

        RatingBar(rating = rating) {
            rating = it
            onRatingFilled(rating)
        }

        Spacer(modifier = Modifier.height(12.dp))

        HorizontalDivider()

        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(text = "Total paid", style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Rp. ${orderDataModel.totalPrice}", style = MaterialTheme.typography.labelMedium)
        }

        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(text = "Points earned", style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "+155 Points", style = MaterialTheme.typography.labelMedium)
        }

        HorizontalDivider()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RatingLayout(
    driverDataModel: DriverDataModel,
    orderDataModel: OrderDataModel,
    initialRating: Int,
    onSelectRating: (Int) -> Unit,
    onGenerateSticker: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    val ratings = getRatings()
    var selectedRating by remember { mutableStateOf(ratings.first { it.rating == initialRating }) }
    var rating by remember { mutableStateOf(initialRating) }
    val selectedItems = remember { mutableStateListOf<String>() }
    val quickRatings = getQuickRatings()
    var input by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(modifier = Modifier.padding(bottom = 4.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = selectedRating.label, textAlign = TextAlign.Start, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.weight(1f))
            RatingBar(rating = rating) {
                rating = it
                onSelectRating(it)
                selectedRating = ratings.first { model -> model.rating == rating }
            }
        }

        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "What did you like about the delivery?", modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), textAlign = TextAlign.Start, color = Color.LightGray
        )

        FlowRow(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in quickRatings.indices) {
                val isSelected = selectedItems.contains(quickRatings[i])
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .background(if (isSelected) GrabPrimaryVariant15 else Color.White)
                        .clickable {
                            if (isSelected) {
                                selectedItems.remove(quickRatings[i])
                            } else {
                                selectedItems.add(quickRatings[i])
                            }
                        }
                        .border(BorderStroke(1.dp, if (isSelected) GrabPrimaryVariant else Color.LightGray), shape = RoundedCornerShape(50.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(quickRatings[i], modifier = Modifier.padding(vertical = 6.dp, horizontal = 16.dp), fontSize = 14.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = input,
            onValueChange = {
                input = it
            },
            placeholder = { Text("Enter Comment") },
            maxLines = 4,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color(0XFFEFEFEF),
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        CardDriverGenerateSticker {
            onGenerateSticker(it)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onSubmit() },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GrabPrimary
            )
        ) {
            Text(text = "Submit")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int,
    maxRating: Int = 5,
    onRatingChange: (Int) -> Unit
) {
    Row(modifier = modifier) {
        for (i in 1..maxRating) {
            IconButton(onClick = { onRatingChange(i) }) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Star",
                    tint = if (i <= rating) Color(0xFFF8D368) else Color.Gray,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RatingPreview() {
    AngelHackGrabTheme {
        RatingScreen(
            orderDataModel = OrderDataModel(totalPrice = "42.000"),
            driverDataModel = DriverDataModel(
                name = "John Doe",
                photoUrl = "https://gallery.poskota.co.id/storage/Foto/aloy-ojol.jpg",
                rating = 4.9,
                vehicle = "Verio 180",
                vehicleNumber = "B 5123 UYT",
                story = "Berani dan Pantang Menyerah merupakan hal yang menggambarkan muhadjirin sebagai sosok driver dengan baju terapi, dimana dia berani menerjang hujan bada mengantarkan penumpang. Hal ini sudah dilakukannya 20 kali"
            ),
            initialStickerDataModel = StickerDataModel(imageUrl = "https://gallery.poskota.co.id/storage/Foto/aloy-ojol.jpg")
        )
    }
}