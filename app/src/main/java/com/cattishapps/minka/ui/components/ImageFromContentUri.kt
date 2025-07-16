package com.cattishapps.minka.ui.components

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cattishapps.minka.R
import com.cattishapps.minka.ui.theme.AlphaRed
import com.cattishapps.minka.util.Spacing

@Composable
fun ImageFromContentUri(
    uri: Uri,
    onRemove: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val bitmap by remember(uri) {
        mutableStateOf(
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                null
            }
        )
    }

    bitmap?.let {
        Box(
            modifier = Modifier
                .padding(bottom = Spacing.large)
                .size(100.dp)
                .border(2.dp, AlphaRed, RoundedCornerShape(Spacing.medium))
        ) {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(Spacing.medium))
            )
            onRemove?.let {
                Icon(
                    painter = painterResource(id = R.drawable.outline_close_small_24),
                    contentDescription = "Remove image",
                    tint = Color.Black,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 10.dp, y = (-10).dp)
                        .size(Spacing.xLarge)
                        .clickable { it() }
                )
            }
        }
    } ?: Text("Failed to load photo")
}
