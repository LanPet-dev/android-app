package com.lanpet.core.common.widget

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.lanpet.core.common.R
import com.lanpet.core.common.createProfileImageUri
import com.lanpet.core.common.rememberCameraPermissionLauncher
import com.lanpet.core.common.rememberCameraTakePictureLauncher
import com.lanpet.core.common.rememberGalleryLauncher
import com.lanpet.domain.model.ProfileType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileImagePicker(
    profileImageUri: Uri?,
    profileType: ProfileType,
    modifier: Modifier = Modifier,
    onImageSelect: (Uri) -> Unit = { },
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var imageUri: Uri? by rememberSaveable {
        mutableStateOf(null)
    }

    // 갤러리 launcher
    val galleryLauncher =
        rememberGalleryLauncher(
            onGetUri = { uri ->
                uri?.let {
                    onImageSelect(it)
                }
            },
        )

    // 카메라 launcher
    val cameraLauncher =
        rememberCameraTakePictureLauncher { success ->
            if (success) {
                imageUri?.let { uri ->
                    onImageSelect(uri)
                }
            }
        }

    // 카메라 권한 launcher
    val cameraPermissionLauncher =
        rememberCameraPermissionLauncher(
            onGrant = {
                val uri = context.createProfileImageUri()
                imageUri = uri
                cameraLauncher.launch(uri)
            },
            onDeny = { Toast.makeText(context, "카메라 권한이 필요합니다", Toast.LENGTH_SHORT).show() },
        )

    ImagePickerView(
        imageUri = profileImageUri,
        profileType = profileType,
    ) {
        scope.launch {
            sheetState.show()
        }
    }

    if (sheetState.isVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {},
            containerColor = Color.Transparent,
        ) {
            IOSActionSheet(
                cancelButton = {
                    ActionButton(
                        text = stringResource(R.string.title_button_close),
                        onClick = {
                            scope.launch {
                                sheetState.hide()
                            }
                        },
                    )
                },
                content = {
                    Column {
                        ActionButton(
                            text = stringResource(R.string.title_button_camera),
                            onClick = {
                                // TODO open camera
                                scope.launch {
                                    sheetState.hide()
                                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            },
                        )
                        ActionButton(
                            text = stringResource(R.string.title_button_gallery),
                            onClick = {
                                scope.launch {
                                    sheetState.hide()
                                    galleryLauncher.launch("image/*")
                                }
                            },
                        )
                    }
                },
            )
        }
    }
}
