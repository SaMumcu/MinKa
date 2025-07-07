package com.cattishapps.minka.ui.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cattishapps.minka.alfasLaboneFontFamily
import com.cattishapps.minka.util.Spacing

@Composable
fun CustomInputDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var what by remember { mutableStateOf("") }
    var whenTo by remember { mutableStateOf("") }
    val isValid = what.length >= 2 && whenTo.length >= 2

    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                tonalElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
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
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = what,
                        onValueChange = { what = it },
                        placeholder = { Text("Go shopping") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Black,
                            unfocusedContainerColor = Color(0x4D990000),
                            disabledContainerColor = Color(0x4D990000),

                            cursorColor = Color(0xFF990000),
                            focusedTextColor = Color(0xFF990000),
                            unfocusedTextColor = Color(0xFF990000),
                            focusedPlaceholderColor = Color.Gray,
                            unfocusedPlaceholderColor = Color.Gray
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
                            unfocusedContainerColor = Color(0x4D990000),
                            disabledContainerColor = Color(0x4D990000),

                            cursorColor = Color.Red,
                            focusedTextColor = Color.Red,
                            unfocusedTextColor = Color.Red,
                            focusedPlaceholderColor = Color.Gray,
                            unfocusedPlaceholderColor = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.height(Spacing.medium))
                    Text(
                        text = "Add.",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isValid) Color.Black else Color.Black.copy(alpha = 0.3f),
                            fontFamily = alfasLaboneFontFamily
                        ),
                        modifier = Modifier.align(Alignment.End).then(
                            if (isValid) Modifier.clickable {
                                println("addtodb")

                            } else Modifier
                        )
                    )
                    Spacer(modifier = Modifier.height(Spacing.large))
                }
            }
        }
    }
}
