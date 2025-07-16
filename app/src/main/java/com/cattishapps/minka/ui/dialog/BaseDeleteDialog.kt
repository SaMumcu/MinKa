package com.cattishapps.minka.ui.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cattishapps.minka.ui.theme.Red
import com.cattishapps.minka.ui.theme.alfasLaboneFontFamily
import com.cattishapps.minka.util.Spacing

@Composable
fun BaseDeleteDialog(
    showDialog: Boolean,
    message: String,
    buttonText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                shape = RoundedCornerShape(Spacing.large),
                color = Color.White,
                tonalElevation = Spacing.small
            ) {
                Column(
                    modifier = Modifier
                        .padding(Spacing.large)
                        .width(150.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Black,
                            fontFamily = alfasLaboneFontFamily
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(Spacing.medium))
                    Text(
                        text = buttonText,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Red,
                            fontWeight = FontWeight.Bold,
                            fontFamily = alfasLaboneFontFamily
                        ),
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable {
                                onDismiss()
                                onConfirm()
                            }
                            .padding(Spacing.small)
                    )
                }
            }
        }
    }
}
