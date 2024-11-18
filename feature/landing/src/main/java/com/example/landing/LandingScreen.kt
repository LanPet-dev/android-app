package com.example.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.PurpleColor
import com.example.designsystem.theme.landingLabel
import java.nio.file.WatchEvent

@Composable
fun LandingScreen() {
    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            PageView(
                listOf(
                    { LandingPage1() },
                    { LandingPage2() },
                    { LandingPage3() },
                )
            )
        }
    }
}

@Composable
fun PageView(
    pages: List<@Composable () -> Unit>,
) {
    val pagerState = rememberPagerState(
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
            beyondViewportPageCount = 3
        ) { page ->
            pages.get(page).invoke()
        }

        // indicator
        Row(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        ) {
            repeat(pages.size) { iteration ->
                key(iteration) {
                    val color =
                        if (pagerState.currentPage == iteration) PurpleColor.Medium else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun LandingPage1() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
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
        ImageSection()
        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun LandingPage2() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
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
        ImageSection()
        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun LandingPage3() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
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
        ImageSection()
        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun Heading(modifier: Modifier = Modifier, text: String) {
    Text(
        text,
        textAlign = TextAlign.Center,
        modifier = modifier,
        style = MaterialTheme.typography.headlineMedium
    )
}

@Composable
fun SubHeading(modifier: Modifier = Modifier, text: String) {
    Text(
        text,
        textAlign = TextAlign.Center,
        modifier = modifier,
        style = MaterialTheme.typography.landingLabel()
    )
}

@Composable
fun ImageSection(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(com.example.designsystem.R.drawable.ic_launcher_background),
        contentDescription = null,
        modifier = modifier
            .size(240.dp)
            .clip(shape = CircleShape)
    )
}

@Composable
fun LandingIndicator() {

}

@Preview(showBackground = true)
@Composable
fun PreviewLandingScreen() {
    LandingScreen()
}