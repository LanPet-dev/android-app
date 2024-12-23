package com.lanpet.core.common

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun rememberCameraPermissionLauncher(
    onGrant: () -> Unit,
    onDeny: () -> Unit,
): ManagedActivityResultLauncher<String, Boolean> =
    rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        if (isGranted) {
            onGrant()
        } else {
            onDeny()
        }
    }

@Composable
fun rememberGalleryLauncher(onGetUri: (Uri?) -> Unit): ManagedActivityResultLauncher<String, Uri?> =
    rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        onGetUri(uri)
    }

@Composable
fun rememberCameraTakePictureLauncher(onResult: (Boolean) -> Unit): ManagedActivityResultLauncher<Uri, Boolean> =
    rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
    ) { success ->
        onResult(success)
    }
