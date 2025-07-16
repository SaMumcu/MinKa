package com.cattishapps.minka.ui.dialog

import android.net.Uri
import androidx.compose.runtime.Composable
import com.cattishapps.minka.data.model.DayNoteEntity

@Composable
fun DeleteImageDialog(
    showDialog: Boolean,
    originalNote: DayNoteEntity,
    imageUri: Uri,
    message: String,
    buttonText: String,
    onDismiss: () -> Unit,
    onDeleteImageConfirm: (DayNoteEntity, Uri) -> Unit,
) {
    BaseDeleteDialog(
        showDialog = showDialog,
        message = message,
        buttonText = buttonText,
        onConfirm = { onDeleteImageConfirm(originalNote, imageUri) },
        onDismiss = onDismiss
    )
}