package com.example.profile.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.GrayColor
import com.example.designsystem.theme.baseHorizontalMargin
import com.example.designsystem.theme.marginLight
import com.example.designsystem.theme.marginMedium
import com.example.profile.R
import com.example.profile.widget.Heading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreateHasPetScreen(
    onNavigateToYesPetScreen: () -> Unit,
    onNavigateToNoPetScreen: () -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar(title = {}) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = MaterialTheme.baseHorizontalMargin())
        ) {
            Spacer(Modifier.weight(0.1f))
            Heading(title = stringResource(R.string.heading_profile_create_has_pet))
            Spacer(Modifier.weight(0.6f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                HasPetSelectButton(
                    onNavigateToYesPetScreen,
                    title = stringResource(R.string.yes_pet_profile_create_has_pet)
                )
                Spacer(Modifier.padding(vertical = marginMedium))
                HasPetSelectButton(
                    onNavigateToNoPetScreen,
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
                onClick = onClick
            )
    ) {
        Canvas(
            modifier = Modifier
                .size(70.dp)
                .background(GrayColor.MEDIUM)
        ) {
        }
        Spacer(Modifier.padding(vertical = marginLight))
        Text(title, style = MaterialTheme.typography.labelLarge)
    }
}

@Preview(showBackground = true, widthDp = 300, heightDp = 500)
@Composable
fun PreviewProfileCreateHasPetScreen() {
    ProfileCreateHasPetScreen(
        {}, {}
    )
}