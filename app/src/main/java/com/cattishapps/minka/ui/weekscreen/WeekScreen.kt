package com.cattishapps.minka.ui.weekscreen

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cattishapps.minka.R
import com.cattishapps.minka.ui.theme.alfasLaboneFontFamily
import com.cattishapps.minka.data.model.DayNoteEntity
import com.cattishapps.minka.ui.Divider
import com.cattishapps.minka.ui.components.ImageFromContentUri
import com.cattishapps.minka.ui.dialog.AddNoteForDayDialog
import com.cattishapps.minka.ui.dialog.DeleteImageDialog
import com.cattishapps.minka.ui.dialog.DeleteNoteDialog
import com.cattishapps.minka.ui.dialog.UpdateNoteForDayDialog
import com.cattishapps.minka.ui.theme.AlphaRed
import com.cattishapps.minka.ui.theme.Red
import com.cattishapps.minka.util.Spacing
import com.cattishapps.minka.util.WEEK_DAYS
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
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
    var showUpdateDialog by remember { mutableStateOf(false) }
    var selectedNote by remember { mutableStateOf<DayNoteEntity?>(null) }
    var uriToDelete by remember { mutableStateOf<Uri?>(null) }
    var showOnDeleteDialog by remember { mutableStateOf(false) }
    var showOnImageDeleteDialog by remember { mutableStateOf(false) }
    var selectedNoteId by remember { mutableStateOf<Int?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val viewModel: DayNoteViewModel = koinViewModel()

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
                                        .clickable {
                                            selectedNote = note
                                            showUpdateDialog = true
                                        }
                                        .padding(Spacing.medium)
                                        .fillMaxWidth()
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
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    contentPadding = PaddingValues(end = 16.dp)
                                ) {
                                    items(note.imageUris) { uri ->
                                        ImageFromContentUri(
                                            uri = uri,
                                            onRemove = {
                                                showOnImageDeleteDialog = true
                                                uriToDelete = uri
                                                selectedNote = note
                                            })
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
                    selectedNote?.let { it ->
                        UpdateNoteForDayDialog(
                            showDialog = showUpdateDialog,
                            originalNote = it,
                            onDismiss = { showUpdateDialog = false },
                            onUpdate = { updatedNote ->
                                viewModel.updateNote(updatedNote)
                            }
                        )
                    }

                    if (showOnDeleteDialog) {
                        selectedNoteId?.let { noteId ->
                            DeleteNoteDialog(
                                showDialog = true,
                                noteId = noteId,
                                message = stringResource(id = R.string.question_delete),
                                buttonText = stringResource(id = R.string.yes),
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
                    if (showOnImageDeleteDialog) {
                        selectedNote?.let { currentNote ->
                            uriToDelete?.let { it ->
                                DeleteImageDialog(
                                    showDialog = true,
                                    originalNote = currentNote,
                                    imageUri = it,
                                    message = stringResource(id = R.string.question_delete),
                                    buttonText = stringResource(id = R.string.yes),
                                    onDeleteImageConfirm = { note, uri ->
                                        val updatedNote = note.copy(
                                            imageUris = note.imageUris.filterNot { it == uri }
                                        )
                                        viewModel.updateNote(updatedNote)
                                        showOnImageDeleteDialog = false
                                        uriToDelete = null
                                    },
                                    onDismiss = {
                                        showOnImageDeleteDialog = false
                                        uriToDelete = null
                                    }
                                )
                            }
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
    val viewModel: DayNoteViewModel = koinViewModel()
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

