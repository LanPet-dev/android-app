package com.lanpet.core.common.widget

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.crop
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.domain.model.ProfileType

@Composable
fun ImagePickerView(
    imageUri: Uri?,
    profileType: ProfileType,
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
            ProfileImage(
                profileType = profileType,
                imageUri = imageUri.toString().ifEmpty { null },
                modifier = Modifier.crop(size = size),
            )
            Image(
                painter = painterResource(com.lanpet.core.common.R.drawable.ic_plus_circle),
                contentDescription = null,
                modifier =
                    Modifier
                        .align(
                            alignment = Alignment.BottomEnd,
                        ).offset(x = (-8).dp, y = (-8).dp)
                        .size(34.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            onEditButtonClick()
                        },
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun ImagePickerViewPreview() {
    LanPetAppTheme {
        ImagePickerView(
            imageUri = null,
            profileType = ProfileType.PET,
        )
    }
}
