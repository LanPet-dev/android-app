package com.lanpet.core.common.widget

import androidx.annotation.StringRes
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.customTypography

@Composable
fun PreparingScreen(
    @StringRes titleResId: Int,
    modifier: Modifier = Modifier,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val rotationFloat by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 10f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    tween(
                        durationMillis = 900,
                        easing = EaseInBounce,
                    ),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "anim",
    )

    val scaleFloat by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    tween(
                        durationMillis = 1500,
                        easing = EaseInOut,
                    ),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "anim",
    )

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .offset(y = maxHeight * 0.2f),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(com.lanpet.core.designsystem.R.drawable.img_preparing),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .size(200.dp)
                            .graphicsLayer {
                                rotationZ = rotationFloat
                                scaleX = scaleFloat
                                scaleY = scaleFloat
                            },
                )
                Text(
                    text = stringResource(titleResId),
                    style = MaterialTheme.customTypography().body1SemiBoldMulti,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreparingScreenPreview() {
    LanPetAppTheme {
        Scaffold {
            Surface(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(it),
            ) {
                PreparingScreen(
                    titleResId = com.lanpet.core.designsystem.R.string.next_button_string,
                )
            }
        }
    }
}
