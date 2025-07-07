package com.cattishapps.minka.ui.dayscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cattishapps.minka.alfasLaboneFontFamily
import com.cattishapps.minka.ui.dialog.CustomInputDialog
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun DayCard(label: String) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(vertical = 4.dp, horizontal = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RectangleShape,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = alfasLaboneFontFamily
                ),
            )
            AnimatedVisibility(visible = expanded) {
                Row {
                    Text(
                        text = "7:00p",
                        modifier = Modifier.padding(top = 8.dp).weight(2f)
                    )
                    Text(
                        text = "Ugly Party",
                        modifier = Modifier.padding(top = 8.dp).weight(3f)
                    )
                    IconButton(
                        onClick = { showDialog = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Note",
                            tint = Color(0xFF990000)
                        )
                    }
                    CustomInputDialog(
                        showDialog = showDialog,
                        onDismiss = { showDialog = false },
                        onConfirm = { _, _ -> }
                    )
                }
            }
        }
    }
}

fun getWeekDays(selectedDate: LocalDate): List<LocalDate> {
    val dayOfWeek = selectedDate.dayOfWeek.value // Pazartesi: 1, Pazar: 7
    val startOfWeek = selectedDate.minusDays((dayOfWeek - 1).toLong())
    return (0..6).map { startOfWeek.plusDays(it.toLong()) }
}

fun getWeekDates(selectedDate: LocalDate): List<LocalDate> {
    val startOfWeek = selectedDate.with(DayOfWeek.MONDAY)
    return (0..6).map { startOfWeek.plusDays(it.toLong()) }
}

@Composable
fun DayScreen(navController: NavController, selectedDate: String) {
    val localDate = LocalDate.parse(selectedDate)
    val weekDays = remember { getWeekDates(localDate) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Seçilen günü index olarak bulup scroll et
        val selectedIndex = weekDays.indexOfFirst { it == localDate }
        if (selectedIndex >= 0) {
            coroutineScope.launch {
                listState.scrollToItem(selectedIndex)
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(weekDays.size) { index ->
                val date = weekDays[index]
                val dayChar = date.dayOfWeek.name.first() // M, T, W, etc.
                val label = "${dayChar}${date.dayOfMonth}"

                DayCard(label = label)
            }
        }
    }
}

