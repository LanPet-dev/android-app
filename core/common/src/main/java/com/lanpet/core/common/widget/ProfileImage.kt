package com.lanpet.core.common.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lanpet.core.common.R
import com.lanpet.core.common.crop
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.VioletColor
import com.lanpet.domain.model.ProfileType

@Composable
fun ProfileImage(
    profileType: ProfileType,
    modifier: Modifier = Modifier,
    size: Dp = 130.dp,
    imageUri: String? = null,
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier.padding(8.dp),
    ) {
        if (imageUri == null) {
            Image(
                painter = painterResource(id = R.drawable.img_default_profile),
                contentDescription = "Profile Image",
                modifier =
                    Modifier
                        .border(
                            width = 2.dp,
                            color = VioletColor.Violet100,
                            shape = CircleShape,
                        ).crop(size = size),
            )
        } else {
            AsyncImage(
                imageUri,
                contentDescription = "Profile Image",
                Modifier
                    .border(
                        width = 2.dp,
                        color = VioletColor.Violet100,
                        shape = CircleShape,
                    ).crop(size = size),
            )
        }
        if (profileType == ProfileType.PET) {
            Image(
                painter = painterResource(id = R.drawable.img_bell2),
                contentDescription = "img_bell",
                modifier = Modifier.scale(size.value/130).offset(y = 8.dp),
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.img_ribbon2),
                contentDescription = "img_ribbon",
                modifier = Modifier.scale(size.value/130).offset(y = 7.dp),
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun PreviewProfileImage() {
    LanPetAppTheme {
        Column {
            ProfileImage(
                profileType = ProfileType.PET,
            )
            ProfileImage(
                profileType = ProfileType.BUTLER,
            )
        }
    }
}
