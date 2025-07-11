package com.cattishapps.minka.ui.weekscreen

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cattishapps.minka.ui.theme.alfasLaboneFontFamily
import com.cattishapps.minka.data.model.DayNoteEntity
import com.cattishapps.minka.ui.Divider
import com.cattishapps.minka.ui.dialog.CustomInputDialog
import com.cattishapps.minka.ui.theme.Red
import com.cattishapps.minka.util.Spacing
import com.cattishapps.minka.util.WEEK_DAYS
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DayCard(
    selectedDate: LocalDate, label: String,
    dayNotes: List<DayNoteEntity>,
    snackbarHostState: SnackbarHostState,
    isInitiallyExpanded: Boolean = false
) {
    var expanded by remember { mutableStateOf(isInitiallyExpanded) }
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val viewModel: DayNoteViewModel = hiltViewModel()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RectangleShape,
    ) {
        Column(modifier = Modifier.padding(Spacing.large)) {
            Text(
                text = label,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = alfasLaboneFontFamily
                ),
            )
            AnimatedVisibility(
                visible = expanded
            ) {
                Column {
                    dayNotes.forEach { note ->
                        Column {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = note.date.format(DateTimeFormatter.ofPattern("HH:mm")),
                                    modifier = Modifier
                                        .padding(top = Spacing.medium)
                                        .weight(2f)
                                )
                                Text(
                                    text = note.note,
                                    modifier = Modifier
                                        .padding(top = Spacing.medium)
                                        .weight(3f)
                                )
                            }
                            Spacer(modifier = Modifier.height(Spacing.medium))
                            if (note.imageUris.isNotEmpty()) {
                                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    items(note.imageUris) { uri ->

                                        ImageFromContentUri(uri = uri)
                                    }
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = Spacing.medium),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = { showDialog = true },
                            modifier = Modifier
                                .size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Note",
                                tint = Red
                            )
                        }
                    }

                    CustomInputDialog(
                        showDialog = showDialog,
                        selectedDate = selectedDate,
                        onDismiss = { showDialog = false },
                        onConfirm = { noteEntity ->
                            coroutineScope.launch {
                                try {
                                    viewModel.addNote(noteEntity)
                                    snackbarHostState.showSnackbar("Note added.")
                                } catch (e: Exception) {
                                    snackbarHostState.showSnackbar("Failed to add note.")
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

fun getWeekDates(selectedDate: LocalDate): List<LocalDate> {
    val startOfWeek = selectedDate.with(DayOfWeek.MONDAY)
    return (0..<WEEK_DAYS).map { startOfWeek.plusDays(it.toLong()) }
}

@Composable
fun WeekScreen(navController: NavController, selectedDate: String) {
    val viewModel: DayNoteViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val localDate = LocalDate.parse(selectedDate)
    val weekDays = remember { getWeekDates(localDate) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val selectedIndex = weekDays.indexOfFirst { it == localDate }
        if (selectedIndex >= 0) {
            coroutineScope.launch {
                listState.scrollToItem(selectedIndex)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { padding ->
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

                viewModel.getAllNotes()
                val notes by viewModel.notes.collectAsState()
                val dayNotes = notes.filter {
                    it.addedDate == date
                }

                Divider(padding = 0.dp)
                DayCard(
                    date,
                    label = label,
                    dayNotes,
                    snackbarHostState = snackbarHostState,
                    isInitiallyExpanded = (date == localDate)
                )
                if (index == WEEK_DAYS - 1) {
                    Divider(padding = 0.dp)
                }
            }
        }
    }
}

@Composable
fun ImageFromContentUri(uri: Uri) {
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
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    } ?: Text("Failed to load photo")
}

