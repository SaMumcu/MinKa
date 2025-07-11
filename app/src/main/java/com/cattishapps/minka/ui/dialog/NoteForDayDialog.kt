package com.cattishapps.minka.ui.dialog

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.cattishapps.minka.ui.theme.alfasLaboneFontFamily
import com.cattishapps.minka.data.model.DayNoteEntity
import com.cattishapps.minka.ui.imagepicker.MultiImagePickerLauncher
import com.cattishapps.minka.ui.theme.AlphaRed
import com.cattishapps.minka.ui.theme.Red
import com.cattishapps.minka.util.Spacing
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.time.LocalDate

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CustomInputDialog(
    showDialog: Boolean,
    selectedDate: LocalDate,
    onDismiss: () -> Unit,
    onConfirm: (DayNoteEntity) -> Unit
) {
    var what by remember { mutableStateOf("") }
    var whenTo by remember { mutableStateOf("") }
    val isValid = what.length >= 2 && whenTo.length >= 2
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val permissionState = rememberPermissionState(permission = permission)

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
                    Text(
                        text = "What?",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontFamily = alfasLaboneFontFamily
                        )
                    )
                    Spacer(modifier = Modifier.height(Spacing.medium))
                    TextField(
                        value = what,
                        onValueChange = { what = it },
                        placeholder = { Text("Go shopping") },
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
                        text = "When?",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontFamily = alfasLaboneFontFamily
                        )
                    )
                    Spacer(modifier = Modifier.height(Spacing.medium))
                    TextField(
                        value = whenTo,
                        onValueChange = { whenTo = it },
                        placeholder = { Text("Tomorrow 530") },
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
                    MultiImagePickerLauncher(
                        onImagesPicked = { pickedUris ->
                            imageUris = pickedUris
                        },
                        trigger = { launchPicker ->
                            Text(
                                text = "Image?",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black.copy(alpha = 0.3f),
                                    fontFamily = alfasLaboneFontFamily
                                ),
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .then(
                                        Modifier.clickable {
                                            if (permissionState.status.isGranted) {
                                                launchPicker()
                                            } else {
                                                permissionState.launchPermissionRequest()
                                            }
                                        }
                                    )
                            )
                        }
                    )
                    if (imageUris.isNotEmpty()) {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(imageUris) { uri ->
                                AsyncImage(
                                    model = uri,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(Spacing.medium))
                    Text(
                        text = "Add.",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isValid) Color.Black else Color.Black.copy(alpha = 0.3f),
                            fontFamily = alfasLaboneFontFamily
                        ),
                        modifier = Modifier
                            .align(Alignment.End)
                            .then(
                                if (isValid) Modifier.clickable {
                                    onDismiss()
                                    onConfirm(
                                        DayNoteEntity(
                                            date = whenTo,
                                            note = what,
                                            addedDate = selectedDate,
                                            imageUris = imageUris
                                        )
                                    )
                                    what = ""
                                    whenTo = ""
                                    imageUris = emptyList()

                                } else Modifier
                            )
                    )
                    Spacer(modifier = Modifier.height(Spacing.medium))
                }
            }
        }
    }
}
