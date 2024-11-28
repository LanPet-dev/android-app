package com.lanpet.profile.widget

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.R as DS_R
import com.lanpet.core.designsystem.theme.crop

@Composable
fun ImagePickerView(imageUri: Uri?, onEditButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box {
            Image(
                painter = if (imageUri != null) {
                    rememberAsyncImagePainter(imageUri)
                } else {
                    painterResource(DS_R.drawable.img_dummy)
                },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.crop()
            )
            Image(
                painter = painterResource(DS_R.drawable.ic_edit),
                contentDescription = null,
                modifier = Modifier
                    .align(
                        alignment = Alignment.BottomEnd
                    )
                    .size(32.dp)
                    .clickable {
                        onEditButtonClick()
                    }
            )
        }
    }
}

@Preview
@Composable
fun PreviewImageWithEdit() {
    LanPetAppTheme {
        ImagePickerView(
            imageUri = null
        ) { }
    }
}