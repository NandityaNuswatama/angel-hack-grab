package com.nandits.angelhackgrab.screen.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun GrabImage(
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
            modifier = modifier
        )
        if (painter.state is AsyncImagePainter.State.Loading) CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}