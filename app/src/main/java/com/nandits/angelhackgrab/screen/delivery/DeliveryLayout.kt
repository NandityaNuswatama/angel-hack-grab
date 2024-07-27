package com.nandits.angelhackgrab.screen.delivery

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nandits.angelhackgrab.R
import com.nandits.angelhackgrab.datamodel.DriverDataModel
import com.nandits.angelhackgrab.datamodel.OrderDataModel
import com.nandits.angelhackgrab.datamodel.StickerDataModel
import com.nandits.angelhackgrab.datamodel.Tip
import com.nandits.angelhackgrab.datamodel.constant.getTips
import com.nandits.angelhackgrab.screen.common.GrabImage
import com.nandits.angelhackgrab.screen.common.GrabRoundImage
import com.nandits.angelhackgrab.screen.common.ZoomableImage
import com.nandits.angelhackgrab.ui.theme.AngelHackGrabTheme
import com.nandits.angelhackgrab.ui.theme.GrabOnSecondary
import com.nandits.angelhackgrab.ui.theme.GrabPrimary
import com.nandits.angelhackgrab.ui.theme.GrabPrimaryVariant
import com.nandits.angelhackgrab.ui.theme.GrabPrimaryVariant15
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryLayout(
    sheetState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    orderDataModel: OrderDataModel,
    driverDataModel: DriverDataModel,
    stickerDataModel: StickerDataModel,
    onCompleteOrderClicked: () -> Unit,
    onGenerateSticker: (String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheetDriverStory by remember { mutableStateOf(false) }
    var showBottomSheetDetailSticker by remember { mutableStateOf(stickerDataModel.imageUrl.isNotEmpty()) }
    var lastPrompt by remember { mutableStateOf("") }

    BackHandler(enabled = sheetState.bottomSheetState.hasExpandedState) {
        coroutineScope.launch {
            sheetState.bottomSheetState.expand()
        }
    }

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetContent = {
            DeliveryTray(
                Modifier,
                orderDataModel,
                driverDataModel,
                stickerDataModel,
                onStoryClicked = {
                    showBottomSheetDriverStory = true
                },
                onCompleteOrderClicked = {
                    onCompleteOrderClicked()
                },
                onGenerateSticker = {
                    lastPrompt = ""
                    onGenerateSticker(it)
                }
            )

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

            if (showBottomSheetDriverStory) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheetDriverStory = false },
                    dragHandle = null
                ) {
                    DriverStoryBottomSheet(driverDataModel) {
                        showBottomSheetDriverStory = false
                    }
                }
            }
        },
        sheetPeekHeight = 100.dp,
        containerColor = Color.White,
        sheetDragHandle = null,
        sheetContainerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ZoomableImage(modifier = Modifier.fillMaxWidth(), imageURL = R.drawable.grab_maps)
        }
    }
}

@Composable
fun DeliveryTray(
    modifier: Modifier = Modifier,
    orderDataModel: OrderDataModel,
    driverDataModel: DriverDataModel,
    stickerDataModel: StickerDataModel,
    onStoryClicked: (DriverDataModel) -> Unit,
    onCompleteOrderClicked: () -> Unit,
    onGenerateSticker: (String) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .background(Color.Transparent)
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        CardDriverState(arrivalTime = orderDataModel.arriveTime)

        CardDriverIdentity(driverDataModel = driverDataModel)

        if (driverDataModel.story.contains("too low", true).not()) {
            CardDriverStory(name = driverDataModel.name, story = driverDataModel.story) {
                onStoryClicked(driverDataModel)
            }
        }

        CardDriverGenerateSticker(stickerDataModel) {
            onGenerateSticker(it)
        }

        CardDriverTip()

        Button(
            onClick = { onCompleteOrderClicked() },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GrabPrimary
            )
        ) {
            Text(text = "Order Selesai")
        }
    }
}

@Composable
fun CardDriverState(arrivalTime: String) {
    Card(shape = RoundedCornerShape(8.dp)) {
        Column(
            modifier = Modifier.background(Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.Top) {
                    Column(Modifier.weight(1f)) {
                        Text(text = "Arriving by $arrivalTime", style = MaterialTheme.typography.titleLarge)
                        Text(text = "Out of delivery", modifier = Modifier.padding(top = 4.dp))
                    }

                    GrabImage(imageURL = R.drawable.il_grab_motor, modifier = Modifier.size(50.dp))
                }

                Spacer(modifier = Modifier.height(34.dp))

                GrabImage(imageURL = R.drawable.il_grab_driver_state, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun CardDriverIdentity(driverDataModel: DriverDataModel) {
    Card(
        shape = RoundedCornerShape(8.dp), modifier = Modifier
            .height(132.dp)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.background(Color.White)
        ) {
            Column(Modifier.padding(16.dp)) {
                Row {
                    GrabRoundImage(imageURL = driverDataModel.photoUrl, modifier = Modifier.size(45.dp))

                    Column(
                        Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                    ) {
                        Row {
                            Text(text = driverDataModel.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(end = 4.dp))
                            Text(text = driverDataModel.rating.toString(), style = MaterialTheme.typography.bodyMedium)
                            Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color(0xFFF8D368))
                        }

                        Row {
                            Text(text = driverDataModel.vehicle, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(end = 4.dp))
                            Text(text = driverDataModel.vehicleNumber, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                ) {
                    GrabImage(
                        imageURL = R.drawable.il_grab_chat_driver_button, modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .height(44.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    GrabImage(
                        imageURL = R.drawable.il_grab_call_driver_button, modifier = Modifier
                            .size(40.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CardDriverStory(name: String, story: String, onClick: () -> Unit) {
    Card(shape = RoundedCornerShape(8.dp), modifier = Modifier
        .height(112.dp)
        .clickable { onClick() }) {
        Column(
            Modifier.background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(), contentAlignment = Alignment.BottomEnd
            ) {
                GrabImage(
                    imageURL = R.drawable.il_grab_quarter_circle_decor, modifier = Modifier
                        .size(80.dp)
                )

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    Text(text = name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(end = 4.dp))
                    Text(text = "\"$story\"", style = MaterialTheme.typography.bodyMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}

@Composable
fun CardDriverGenerateSticker(stickerDataModel: StickerDataModel = StickerDataModel(), onGenerateClicked: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp), modifier = Modifier.height(240.dp)
    ) {
        Column(Modifier.background(Color.White)) {

            var input by remember { mutableStateOf("") }

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
                val imageSize = if (stickerDataModel.imageUrl.isEmpty()) 120.dp else 160.dp
                GrabImage(
                    imageURL = stickerDataModel.imageUrl.ifEmpty { R.drawable.il_grab_meme_decor }, modifier = Modifier
                        .width(166.dp)
                        .height(imageSize)
                        .align(Alignment.BottomStart)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 16.dp)
                ) {
                    Text(text = "Cheer Up Your Driver with Stickers!", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp))

                    Row {
                        Spacer(Modifier.width(112.dp))

                        Column {
                            TextField(
                                value = input,
                                onValueChange = { input = it },
                                placeholder = { Text("Orang banyak makan") },
                                maxLines = 2,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color(0XFFEFEFEF),
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(78.dp)
                            )

                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Text(text = "*contribute to solve climate", style = MaterialTheme.typography.labelMedium, color = GrabPrimary)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = "Rp. 500", style = MaterialTheme.typography.bodySmall)
                            }

                            Button(
                                onClick = { onGenerateClicked(input) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(4.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GrabPrimary
                                )
                            ) {
                                Text(text = "Generate")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardDriverTip(availableTips: List<Tip> = getTips()) {

    val keyboardController = LocalSoftwareKeyboardController.current
    var selectedTip by remember { mutableStateOf<String?>(null) }
    var input by remember { mutableStateOf("") }

    Card(
        shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = Color.White), modifier = Modifier
    ) {
        Column(Modifier.background(Color.White)) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(text = "Show your appreciation by adding a tip. 100% of the tip goes to driver.", style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.size(12.dp))

                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                ) {
                    items(availableTips.chunked(2)) { row ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            row.forEach { tip ->
                                TipItem(
                                    modifier = Modifier.weight(0.5f),
                                    tip = tip,
                                    isSelected = tip.amount == selectedTip
                                ) {
                                    keyboardController?.hide()
                                    selectedTip = if (selectedTip == it) null else it
                                    input = ""
                                }
                                Spacer(modifier = Modifier.size(8.dp))
                            }
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }

                TextField(
                    value = input,
                    onValueChange = {
                        input = it
                        selectedTip = null
                    },
                    placeholder = { Text("Enter other amount") },
                    maxLines = 1,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color(0XFFEFEFEF),
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun TipItem(modifier: Modifier, tip: Tip, isSelected: Boolean, onClick: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, if (isSelected) GrabPrimaryVariant else GrabOnSecondary),
        modifier = modifier.height(44.dp),
        onClick = { onClick(tip.amount) },
    ) {
        Column(Modifier.background(if (isSelected) GrabPrimaryVariant15 else Color.White)) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tip.amount,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp)
                )
            }
        }
    }
}

@Composable
fun DriverStoryBottomSheet(driverDataModel: DriverDataModel, onCloseClicked: () -> Unit) {
    Column(Modifier.background(Color.White)) {
        Column(
            Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                GrabRoundImage(imageURL = driverDataModel.photoUrl, modifier = Modifier.size(45.dp))
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "The Story of ${driverDataModel.name}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(end = 4.dp, start = 16.dp)
                )
            }

            HorizontalDivider(Modifier.padding(vertical = 8.dp))

            Text(text = "\"${driverDataModel.story}\"", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(end = 4.dp, start = 16.dp))

            Spacer(modifier = Modifier.padding(4.dp))

            Button(
                onClick = { onCloseClicked() },
                border = BorderStroke(1.dp, Color.DarkGray),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(text = "Close", style = MaterialTheme.typography.titleSmall, color = Color.DarkGray)
            }

            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun DetailStickerBottomSheet(stickerDataModel: StickerDataModel, onRegenerateClicked: (String) -> Unit, onUseClicked: (String) -> Unit) {
    Column(
        Modifier
            .background(Color.White)
            .padding(top = 8.dp, bottom = 24.dp)
    ) {
        Column(
            Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Cheer Up Your Driver with Stickers!",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(end = 4.dp, start = 16.dp, bottom = 12.dp)
            )

            GrabImage(imageURL = stickerDataModel.imageUrl, modifier = Modifier.size(width = 180.dp, height = 140.dp), contentScale = ContentScale.Fit)

            Spacer(modifier = Modifier.padding(4.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Button(
                    onClick = { onRegenerateClicked(stickerDataModel.prompt) },
                    border = BorderStroke(1.dp, Color.DarkGray),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "Regenerate: Rp. 500", style = MaterialTheme.typography.titleSmall, color = Color.DarkGray)
                }

                Button(
                    onClick = { onUseClicked(stickerDataModel.imageUrl) },
                    border = BorderStroke(1.dp, Color.DarkGray),
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Text(text = "Use Sticker", style = MaterialTheme.typography.titleSmall, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun OrderPreview() {
    AngelHackGrabTheme {
        DeliveryLayout(
            orderDataModel = OrderDataModel(arriveTime = "10:00 AM"),
            driverDataModel = DriverDataModel(
                name = "John Doe",
                photoUrl = "https://gallery.poskota.co.id/storage/Foto/aloy-ojol.jpg",
                rating = 4.9,
                vehicle = "Verio 180",
                vehicleNumber = "B 5123 UYT",
                story = "Berani dan Pantang Menyerah merupakan hal yang menggambarkan muhadjirin sebagai sosok driver dengan baju terapi, dimana dia berani menerjang hujan bada mengantarkan penumpang. Hal ini sudah dilakukannya 20 kali"
            ),
            stickerDataModel = StickerDataModel("", "https://gallery.poskota.co.id/storage/Foto/aloy-ojol.jpg"),
            onCompleteOrderClicked = {

            },
            onGenerateSticker = {

            }
        )
    }
}
