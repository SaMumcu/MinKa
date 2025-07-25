package com.cattishapps.minka.ui.dialog

import androidx.compose.runtime.Composable

@Composable
fun DeleteNoteDialog(
    showDialog: Boolean,
    noteId: Int,
    message: String,
    buttonText: String,
    onDeleteConfirm: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    BaseDeleteDialog(
        showDialog = showDialog,
        message = message,
        buttonText = buttonText,
        onConfirm = { onDeleteConfirm(noteId) },
        onDismiss = onDismiss
    )
}