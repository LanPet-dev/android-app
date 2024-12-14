package com.lanpet.profile.screen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.widget.CommonButton
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.profile.R
import com.lanpet.profile.widget.Heading
import com.lanpet.profile.widget.HeadingHint
import com.lanpet.core.designsystem.R as DS_R

@Composable
fun ProfileCreateDoneScreen(
    modifier: Modifier = Modifier,
    onNavigateToMyProfile: () -> Unit = {},
) {
    Scaffold {
        Box(
            modifier =
                Modifier
                    .padding(it)
                    .padding(
                        horizontal = LanPetDimensions.Margin.Layout.horizontal,
                        vertical = LanPetDimensions.Margin.Layout.vertical,
                    ),
        ) {
            Column(
                Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxxLarge))
                Heading(title = stringResource(R.string.heading_profile_create_done1))
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))
                Heading(title = stringResource(R.string.heading_profile_create_done2))
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.medium))
                HeadingHint(title = stringResource(R.string.sub_heading_profile_create_done))
                Spacer(Modifier.weight(0.3f))
                RippleProfileContainer()
                Spacer(Modifier.weight(1f))
                CommonButton(
                    title = stringResource(DS_R.string.start_button_string),
                ) {
                    onNavigateToMyProfile()
                }
                Spacer(Modifier.padding(bottom = LanPetDimensions.Spacing.xxSmall))
            }
        }
    }
}

@Composable
fun ModernRippleEffect(
    modifier: Modifier = Modifier,
    circleColor: Color = Color(0xFFE8EEFF),
) {
    val transition = rememberInfiniteTransition(label = "ripple")

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        // 여러 개의 파동 효과를 위한 레이어
        repeat(3) { index ->
            val delay = index * 1000

            val scale by transition.animateFloat(
                initialValue = 0.6f,
                targetValue = 2f,
                animationSpec =
                    infiniteRepeatable(
                        animation =
                            tween(
                                durationMillis = 3000,
                                delayMillis = delay,
                                easing = LinearEasing,
                            ),
                        repeatMode = RepeatMode.Restart,
                    ),
                label = "scale",
            )

            val alpha by transition.animateFloat(
                initialValue = 0.3f,
                targetValue = 0f,
                animationSpec =
                    infiniteRepeatable(
                        animation =
                            tween(
                                durationMillis = 3000,
                                delayMillis = delay,
                                easing = LinearEasing,
                            ),
                        repeatMode = RepeatMode.Restart,
                    ),
                label = "alpha",
            )

            Canvas(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            this.alpha = alpha
                        },
            ) {
                drawCircle(
                    color = circleColor,
                    radius = size.minDimension / 4,
                )
            }
        }

        // 중앙 컨텐츠를 위한 공간
        Box(
            modifier =
                Modifier
                    .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            // 여기에 프로필 이미지나 아이콘을 추가할 수 있습니다
        }
    }
}

// 사용 예시
@Composable
fun RippleProfileContainer(modifier: Modifier = Modifier) {
    Box(
        modifier =
            Modifier
                .size(300.dp)
                .padding(32.dp),
        contentAlignment = Alignment.Center,
    ) {
        ModernRippleEffect()
        Image(
            painter = painterResource(id = com.lanpet.core.designsystem.R.drawable.img_family),
            contentDescription = "Profile Image",
            modifier = Modifier.size(200.dp),
        )

        // 여기에 프로필 이미지를 추가
        // Image() 또는 Icon() 컴포넌트 사용
    }
}

@PreviewLightDark
@Composable
private fun PreviewProfileCreateDoneScreen() {
    LanPetAppTheme {
        ProfileCreateDoneScreen {
        }
    }
}
