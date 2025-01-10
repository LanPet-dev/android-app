package com.lanpet.feature.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.lanpet.core.auth.CognitoAuthManager
import com.lanpet.core.common.crop
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customColorScheme

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Scaffold {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(
                        horizontal = LanPetDimensions.Margin.Layout.horizontal,
                        vertical = LanPetDimensions.Margin.Layout.horizontal,
                    ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxLarge))
            Heading(
                text = stringResource(R.string.heading_login_screen),
            )
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxLarge))
            ImageSection(
                imagePainter = painterResource(id = com.lanpet.core.designsystem.R.drawable.img_login1),
            )
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(
                shape =
                    RoundedCornerShape(
                        LanPetDimensions.Corner.xSmall,
                    ),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.customColorScheme.buttonBackground,
                            shape = RoundedCornerShape(LanPetDimensions.Corner.xSmall),
                        ),
                onClick = {
                    CognitoAuthManager(context).startGoogleSignIn()
                },
            ) {
                Text(
                    text = stringResource(R.string.button_login_with_google),
                    Modifier.padding(LanPetDimensions.Spacing.small),
                    style =
                        MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.customColorScheme.buttonText,
                        ),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
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
        style = MaterialTheme.typography.headlineMedium,
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
//        style = MaterialTheme.typography.landingLabel()
    )
}

@Composable
private fun ImageSection(
    imagePainter: Painter,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = imagePainter,
        contentDescription = null,
        modifier =
            modifier.crop(
                size = 300.dp,
            ),
    )
}

@PreviewLightDark
@Composable
private fun PreviewLoginScreen() {
    LanPetAppTheme {
        LoginScreen()
    }
}
