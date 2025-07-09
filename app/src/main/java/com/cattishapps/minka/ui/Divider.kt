package com.cattishapps.minka.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cattishapps.minka.util.Spacing

@Composable
fun Divider(
    padding: Dp = Spacing.medium
) {
    HorizontalDivider(
        modifier = Modifier
            .padding(vertical = padding)
            .fillMaxWidth()
            .height(1.dp),
        color = Color.Black
    )
}