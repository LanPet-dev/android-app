package com.lanpet.feature.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.common.crop
import com.lanpet.core.auth.CognitoAuthManager

@Composable
fun LoginScreen() {
    val context = LocalContext.current

    Scaffold {
        Column(
             modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(
                    horizontal = LanPetDimensions.Margin.Layout.horizontal,
                    vertical = LanPetDimensions.Margin.Layout.horizontal
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxLarge))
            Heading(
                text = stringResource(R.string.heading_login_screen)
            )
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxLarge))
            ImageSection(
                imagePainter = painterResource(id = com.lanpet.core.designsystem.R.drawable.img_login1)
            )
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(
                shape = RoundedCornerShape(
                    LanPetDimensions.Corner.xSmall
                ),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    CognitoAuthManager(context).startGoogleSignIn()
                },
            ) {
                Text(
                    text = stringResource(R.string.button_login_with_google),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
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
//        style = MaterialTheme.typography.landingLabel()
    )
}

@Composable
private fun ImageSection(modifier: Modifier = Modifier, imagePainter: Painter) {
    Image(
        painter = imagePainter,
        contentDescription = null,
        modifier = modifier.crop(
            size = 300.dp
        )
    )
}

@PreviewLightDark
@Composable
fun PreviewLoginScreen() {
    LanPetAppTheme {
        LoginScreen()

    }
}