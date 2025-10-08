package com.example.nba.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

private sealed interface RemoteImageState {
    data object Loading : RemoteImageState
    data object Empty : RemoteImageState
    data class Success(val url: String) : RemoteImageState
}

@Composable
fun RemoteImageBox(
    size: Dp,
    placeholderRes: Int,
    contentDescriptionRes: Int,
    reloadKey: Any?,
    loader: suspend () -> String?
) {
    var state by remember { mutableStateOf<RemoteImageState>(RemoteImageState.Loading) }

    LaunchedEffect(reloadKey) {
        state = RemoteImageState.Loading
        val url = try { loader() } catch (_: Exception) { null }
        state = if (!url.isNullOrBlank()) RemoteImageState.Success(url) else RemoteImageState.Empty
    }

    when (val s = state) {
        RemoteImageState.Loading -> {
            Box(Modifier.size(size), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        RemoteImageState.Empty -> {
            Image(
                painter = painterResource(placeholderRes),
                contentDescription = stringResource(contentDescriptionRes),
                modifier = Modifier.size(size)
            )
        }
        is RemoteImageState.Success -> {
            GlideImage(
                imageModel = { s.url },
                modifier = Modifier.size(size),
                imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                loading = {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                },
                failure = {
                    Image(
                        painter = painterResource(placeholderRes),
                        contentDescription = stringResource(contentDescriptionRes)
                    )
                }
            )
        }
    }
}
