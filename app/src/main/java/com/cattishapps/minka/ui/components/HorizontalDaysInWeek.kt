package com.cattishapps.minka.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.cattishapps.minka.R

@Composable
fun HorizontalDaysInWeek() {
    val context = LocalContext.current
    val daysOfWeek = context.resources.getStringArray(R.array.days_of_week).toList()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        daysOfWeek.forEach { day ->
            Text(text = day, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        }
    }
}