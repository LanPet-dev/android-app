package com.lanpet.core.common.widget

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.lanpet.core.common.crop
import com.lanpet.core.designsystem.R
import com.lanpet.core.designsystem.theme.LanPetAppTheme

@Composable
fun ProfileImageWithPicker(
    imageUri: Uri?,
    onImagePick: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
        ) { uri: Uri? ->
            uri?.let {
                onImagePick(it)
            }
        }

    CommonImagePickerView(
        imageUri,
    ) {
        launcher.launch("image/*")
    }
}

@Composable
fun CommonImagePickerView(
    imageUri: Uri?,
    modifier: Modifier = Modifier,
    size: Dp = 130.dp,
    onEditButtonClick: () -> Unit = {},
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Box {
            Image(
                painter =
                    if (imageUri != null) {
                        rememberAsyncImagePainter(imageUri)
                    } else {
                        painterResource(R.drawable.img_dummy)
                    },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.crop(size = size),
            )
            Image(
                painter = painterResource(R.drawable.ic_edit),
                contentDescription = null,
                modifier =
                    Modifier
                        .align(
                            alignment = Alignment.BottomEnd,
                        ).size(32.dp)
                        .clip(
                            shape = CircleShape,
                        ).clickable {
                            onEditButtonClick()
                        },
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewProfileImageWithPicker() {
    LanPetAppTheme {
        ProfileImageWithPicker(
            imageUri = null,
            onImagePick = {},
        )
    }
}
