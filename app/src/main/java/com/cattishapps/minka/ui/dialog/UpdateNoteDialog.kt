package com.cattishapps.minka.ui.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cattishapps.minka.R
import com.cattishapps.minka.data.model.DayNoteEntity
import com.cattishapps.minka.ui.theme.AlphaRed
import com.cattishapps.minka.ui.theme.Red
import com.cattishapps.minka.ui.theme.alfasLaboneFontFamily
import com.cattishapps.minka.util.Spacing

@Composable
fun UpdateNoteForDayDialog(
    showDialog: Boolean,
    originalNote: DayNoteEntity,
    onDismiss: () -> Unit,
    onUpdate: (DayNoteEntity) -> Unit
) {
    var what by remember { mutableStateOf(originalNote.note) }
    var whenTo by remember { mutableStateOf(originalNote.date) }
    val isValid = what.length >= 2 && whenTo.length >= 2
    val isChanged = what != originalNote.note || whenTo != originalNote.date
    var imageUris by remember { mutableStateOf(originalNote.imageUris) }

    val isUpdateable = isValid && isChanged

    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                shape = RoundedCornerShape(Spacing.large),
                color = Color.White,
                tonalElevation = Spacing.medium
            ) {
                Column(
                    modifier = Modifier
                        .padding(Spacing.large)
                        .width(300.dp)
                ) {
                    Spacer(modifier = Modifier.height(Spacing.medium))
                    TextField(
                        value = whenTo,
                        onValueChange = { whenTo = it },
                        placeholder = { Text(originalNote.date) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Black,
                            unfocusedContainerColor = AlphaRed,
                            disabledContainerColor = AlphaRed,

                            cursorColor = Red,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Red,
                            focusedPlaceholderColor = AlphaRed,
                            unfocusedPlaceholderColor = AlphaRed
                        ),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None
                        )
                    )
                    Spacer(modifier = Modifier.height(Spacing.medium))
                    TextField(
                        value = what,
                        onValueChange = { what = it },
                        placeholder = { Text(originalNote.note) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Black,
                            unfocusedContainerColor = AlphaRed,
                            disabledContainerColor = AlphaRed,

                            cursorColor = Red,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Red,
                            focusedPlaceholderColor = AlphaRed,
                            unfocusedPlaceholderColor = AlphaRed
                        ),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None
                        )
                    )
                    Spacer(modifier = Modifier.height(Spacing.medium))
                    Text(
                        text = stringResource(R.string.update),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isUpdateable) Color.Black else Color.Black.copy(alpha = 0.3f),
                            fontFamily = alfasLaboneFontFamily
                        ),
                        modifier = Modifier
                            .align(Alignment.End)
                            .then(
                                if (isUpdateable) Modifier.clickable {
                                    onUpdate(
                                        originalNote.copy(
                                            note = what,
                                            date = whenTo,
                                            imageUris = imageUris
                                        )
                                    )
                                    onDismiss()
                                } else Modifier
                            )
                    )
                }
            }
        }
    }
}