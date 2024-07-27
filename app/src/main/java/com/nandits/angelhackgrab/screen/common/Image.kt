package com.nandits.angelhackgrab.screen.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun GrabImage(
    modifier: Modifier = Modifier,
    imageURL: Any?,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    if (imageURL is Int) {
        val painter = painterResource(id = imageURL)
        Image(
            painter = painter,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    } else {
        val painter = rememberAsyncImagePainter(
            model = imageURL
        )
        Box {
            Image(
                painter = painter,contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = modifier
            )
            if (painter.state is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun GrabRoundImage(
    modifier: Modifier = Modifier,
    imageURL: Any?,
    contentDescription: String? = null,
) {
    val painter = rememberAsyncImagePainter(
        model = imageURL
    )
    Box {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = modifier.clip(CircleShape),
        )
        if (painter.state is AsyncImagePainter.State.Loading) CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
fun ZoomableImage(
    imageURL: Int,
    modifier: Modifier = Modifier
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val imageSize = remember { mutableStateOf(Size.Zero) }
    var initialized by remember { mutableStateOf(false) }

    Box(modifier = modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTransformGestures { centroid, pan, zoom, rotation ->
                scale = (scale * zoom).coerceAtLeast(1f)
                val maxX = (imageSize.value.width * scale - size.width).coerceAtLeast(0f)
                val maxY = (imageSize.value.height * scale - size.height).coerceAtLeast(0f)
                offset = Offset(
                    x = offset.x.coerceIn(-maxX, 0f) +pan.x,
                    y = offset.y.coerceIn(-maxY, 0f) + pan.y
                )
            }
        }
    ) {
        Image(
            painter = painterResource(id = imageURL),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize() // Make the image fill the parent
                .onGloballyPositioned { coordinates ->
                    if (!initialized) {
                        val imageWidth = coordinates.size.width
                        val imageHeight = coordinates.size.height
                        val parentWidth = coordinates.parentLayoutCoordinates?.size?.width ?: 0
                        val parentHeight = coordinates.parentLayoutCoordinates?.size?.height ?: 0

                        if (imageWidth > 0 && imageHeight > 0 && parentWidth > 0 && parentHeight > 0) {
                            val scaleX = parentWidth.toFloat() / imageWidth.toFloat()
                            val scaleY = parentHeight.toFloat() / imageHeight.toFloat()
                            scale = maxOf(scaleX, scaleY)

                            val offsetX = (parentWidth - imageWidth * scale) / 2
                            val offsetY = (parentHeight - imageHeight * scale) / 2
                            offset = Offset(offsetX, offsetY)

                            initialized = true
                        }
                    }
                    imageSize.value = coordinates.size.toSize()
                }
                .offset { IntOffset(offset.x.toInt(), offset.y.toInt()) }
                .scale(scale),
            contentScale = ContentScale.Crop
        )
    }
}