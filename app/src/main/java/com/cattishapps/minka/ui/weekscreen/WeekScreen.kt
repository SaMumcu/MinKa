package com.cattishapps.minka.ui.weekscreen

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cattishapps.minka.R
import com.cattishapps.minka.ui.theme.alfasLaboneFontFamily
import com.cattishapps.minka.data.model.DayNoteEntity
import com.cattishapps.minka.ui.Divider
import com.cattishapps.minka.ui.dialog.AddNoteForDayDialog
import com.cattishapps.minka.ui.dialog.DeleteNoteDialog
import com.cattishapps.minka.ui.theme.AlphaRed
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
    var showOnDeleteDialog by remember { mutableStateOf(false) }
    var selectedNoteId by remember { mutableStateOf<Int?>(null) }
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
                Column(
                    modifier = Modifier.padding(Spacing.medium)
                ) {
                    dayNotes.forEach { note ->
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = Color.Black.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(Spacing.medium)
                                    )
                                    .background(
                                        AlphaRed,
                                        shape = RoundedCornerShape(Spacing.medium)
                                    )
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_close_small_24),
                                    contentDescription = "Options",
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .offset(x = 10.dp, y = (-10).dp)
                                        .size(Spacing.xLarge)
                                        .clickable {
                                            showOnDeleteDialog = true
                                            selectedNoteId = note.id
                                        }
                                )

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
                                ) {
                                    Row {
                                        IconWithText(
                                            text = note.date.format(DateTimeFormatter.ofPattern("HH:mm")),
                                            iconResId = R.drawable.outline_calendar_clock_24
                                        )
                                    }
                                    Row {
                                        IconWithText(
                                            text = note.note,
                                            iconResId = R.drawable.outline_architecture_24
                                        )
                                    }
                                }
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
                                .size(Spacing.xLarge)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Note",
                                tint = Red
                            )
                        }
                    }

                    AddNoteForDayDialog(
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
                    if (showOnDeleteDialog) {
                        selectedNoteId?.let { noteId ->
                            DeleteNoteDialog(
                                showDialog = true,
                                noteId = noteId,
                                message = "Delete?",
                                buttonText = "Yes.",
                                onDeleteConfirm = { noteId ->
                                    viewModel.deleteNoteById(noteId)
                                    showOnDeleteDialog = false
                                    selectedNoteId = null
                                },
                                onDismiss = {
                                    showOnDeleteDialog = false
                                    selectedNoteId = null
                                }
                            )
                        }
                    }
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
fun IconWithText(text: String, iconResId: Int) {
    Row(
        modifier = Modifier
            .padding(Spacing.small)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = "Calendar icon",
            modifier = Modifier.size(Spacing.large)
        )
        Spacer(modifier = Modifier.width(Spacing.small))
        Text(
            text = text,
            modifier = Modifier
        )
    }
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
                .padding(bottom = Spacing.large)
                .size(100.dp)
                .clip(RoundedCornerShape(Spacing.medium))
        )
    } ?: Text("Failed to load photo")
}

