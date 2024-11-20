package com.example.profile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.LanPetDimensions
import com.example.designsystem.theme.widgets.LanPetTopAppBar
import com.example.profile.R
import com.example.profile.widget.Heading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreateHasPetScreen(
    onNavigateToYesPetScreen: () -> Unit,
    onNavigateToNoPetScreen: () -> Unit,
) {
    Scaffold(
        topBar = {
            LanPetTopAppBar(
                title = {},
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(
                    horizontal = LanPetDimensions.Margin.Layout.horizontal,
                    vertical = LanPetDimensions.Margin.Layout.vertical,
                )
        ) {
            Spacer(Modifier.weight(0.1f))
            Heading(title = stringResource(R.string.heading_profile_create_has_pet))
            Spacer(Modifier.weight(0.6f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                HasPetSelectButton(
                    onNavigateToNoPetScreen,
                    title = stringResource(R.string.yes_pet_profile_create_has_pet)
                )
                Spacer(Modifier.padding(vertical = LanPetDimensions.Margin.medium))
                HasPetSelectButton(
                    onNavigateToYesPetScreen,
                    title = stringResource(R.string.no_pet_profile_create_has_pet)
                )
            }
            Spacer(Modifier.weight(1f))
        }
    }
}

@Composable
fun HasPetSelectButton(onClick: () -> Unit, title: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentSize()
            .clickable(
                onClick = onClick,
            )
    ) {
        Image(
            painter = painterResource(com.example.designsystem.R.drawable.img_dummy),
            contentDescription = null,
            modifier = Modifier
                .size(
                    110.dp
                )
                .clip(
                    RoundedCornerShape(LanPetDimensions.Corner.small)
                ),

            )
        Spacer(Modifier.padding(vertical = LanPetDimensions.Margin.small))
        Text(title, style = MaterialTheme.typography.labelLarge)
    }
}

@Preview(showBackground = true, widthDp = 500, heightDp = 700)
@Composable
fun PreviewProfileCreateHasPetScreen() {
    ProfileCreateHasPetScreen(
        {}, {}
    )
}