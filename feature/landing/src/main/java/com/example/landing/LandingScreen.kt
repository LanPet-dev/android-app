package com.example.landing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LandingScreen() {
    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Heading(stringResource(R.string.text_landing_heading_1))
                SubHeading(stringResource(R.string.text_landing_subheading_1))
            }
        }
    }
}

@Composable
fun Heading(text: String) {
    Text(
        text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineMedium
    )
}

@Composable
fun SubHeading(text: String) {
    Text(
        text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.labelLarge
    )
}

@Composable
fun ImageSection() {
}

@Composable
fun LandingIndicator() {

}

@Preview(showBackground = true)
@Composable
fun PreviewLandingScreen() {
    LandingScreen()
}