package com.lanpet.feature.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lanpet.core.common.widget.CommonButton
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.PurpleColor
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.feature.landing.viewmodel.LandingViewModel

@Composable
fun LandingScreen(
    modifier: Modifier = Modifier,
    landingViewModel: LandingViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit = {},
) {
    Surface {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = LanPetDimensions.Margin.Layout.horizontal,
                        vertical = LanPetDimensions.Margin.Layout.vertical,
                    ),
        ) {
            PageView(
                listOf(
                    { LandingPage1() },
                    { LandingPage2() },
                    {
                        LandingPage3(
                            navigateToLogin = onNavigateToLogin,
                            saveShouldShowLanding = landingViewModel::saveShouldShowLanding,
                        )
                    },
                ),
            )
        }
    }
}

@Composable
fun PageView(
    pages: List<@Composable () -> Unit>,
    modifier: Modifier = Modifier,
) {
    val pagerState =
        rememberPagerState(
            initialPage = 0,
        ) {
            return@rememberPagerState pages.size
        }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        HorizontalPager(
            state = pagerState,
            key = { it },
            beyondViewportPageCount = 3,
        ) { page ->
            pages.get(page).invoke()
        }

        // indicator
        Row(
            modifier =
                Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .padding(bottom = 200.dp),
        ) {
            repeat(pages.size) { iteration ->
                key(iteration) {
                    LandingIndicatorItem(
                        isActive = pagerState.currentPage == iteration,
                    )
                }
            }
        }
    }
}

@Composable
fun LandingPage1(modifier: Modifier = Modifier) {
    Column(
        modifier =
            Modifier
                .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.weight(0.5f))
        Heading(
            text = stringResource(R.string.text_landing_heading_1),
        )
        Spacer(Modifier.padding(vertical = 12.dp))
        SubHeading(
            text = stringResource(R.string.text_landing_subheading_1),
        )
        Spacer(Modifier.weight(0.3f))
        ImageSection(
            imagePainter = painterResource(id = com.lanpet.core.designsystem.R.drawable.img_landing2),
        )
        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun LandingPage2(modifier: Modifier = Modifier) {
    Column(
        modifier =
            Modifier
                .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.weight(0.5f))
        Heading(
            text = stringResource(R.string.text_landing_heading_2),
        )
        Spacer(Modifier.padding(vertical = 12.dp))
        SubHeading(
            text = stringResource(R.string.text_landing_subheading_2),
        )
        Spacer(Modifier.weight(0.3f))
        ImageSection(
            imagePainter = painterResource(id = com.lanpet.core.designsystem.R.drawable.img_landing1),
        )
        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun LandingPage3(
    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit = {},
    saveShouldShowLanding: (Boolean) -> Unit = {},
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.weight(0.5f))
        Heading(
            text = stringResource(R.string.text_landing_heading_3),
        )
        Spacer(Modifier.padding(vertical = 12.dp))
        SubHeading(
            text = stringResource(R.string.text_landing_subheading_3),
        )
        Spacer(Modifier.weight(0.3f))
        ImageSection(
            imagePainter = painterResource(id = com.lanpet.core.designsystem.R.drawable.img_landing3),
        )
        Spacer(Modifier.weight(1f))
        CommonButton(
            title = stringResource(R.string.button_start_landing),
            modifier = Modifier.padding(top = 24.dp, bottom = 24.dp),
            onClick = {
                saveShouldShowLanding(false)
                navigateToLogin()
            },
        )
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun Heading(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text,
        textAlign = TextAlign.Center,
        modifier = modifier,
        style = MaterialTheme.customTypography().title1SemiBoldMulti,
    )
}

@Composable
fun SubHeading(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text,
        textAlign = TextAlign.Center,
        modifier = modifier,
        style = MaterialTheme.customTypography().body1RegularMulti,
    )
}

@Composable
fun ImageSection(
    imagePainter: Painter,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = imagePainter,
        contentDescription = null,
        modifier = modifier.size(300.dp),
    )
}

@Composable
fun LandingIndicatorItem(
    isActive: Boolean,
    modifier: Modifier = Modifier,
) {
    val color =
        if (isActive) PurpleColor.MEDIUM else Color.LightGray
    Box(
        modifier =
            Modifier
                .padding(6.dp)
                .clip(CircleShape)
                .background(color)
                .size(8.dp),
    )
}

@PreviewLightDark
@Composable
private fun PreviewLandingScreen() {
    LanPetAppTheme {
        LandingScreen {}
    }
}
