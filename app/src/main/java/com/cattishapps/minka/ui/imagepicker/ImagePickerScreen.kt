package com.cattishapps.minka.ui.imagepicker

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun MultiImagePickerLauncher(
    onImagesPicked: (List<Uri>) -> Unit,
    trigger: @Composable ((launchPicker: () -> Unit) -> Unit)
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments()
    ) { uris ->
        uris.forEach { uri ->
            try {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }

        onImagesPicked(uris)
    }

    trigger { launcher.launch(arrayOf("image/*")) }
}
