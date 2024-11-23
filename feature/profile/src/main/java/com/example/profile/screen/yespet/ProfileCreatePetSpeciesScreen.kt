package com.example.profile.screen.yespet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.designsystem.R as DS_R
import com.example.designsystem.theme.LanPetAppTheme
import com.example.designsystem.theme.LanPetDimensions
import com.example.designsystem.theme.widgets.CommonButton
import com.example.designsystem.theme.widgets.LanPetTopAppBar
import com.example.designsystem.theme.widgets.TextFieldWithDeleteButton
import com.example.profile.R
import com.example.profile.widget.Heading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreatePetSpeciesScreen(
    onNavigateToPetBio: () -> Unit = { }
) {
    Scaffold(
        topBar = {
            LanPetTopAppBar(
                title = {
                    Text(stringResource(R.string.appbar_title_profile_create))
                },
                actions = {
                    Text("3/4 ")
                }
            )
        },
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .padding(
                    horizontal = LanPetDimensions.Margin.Layout.horizontal,
                    vertical = LanPetDimensions.Margin.Layout.vertical
                )
        ) {
            Column {
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
                Heading(title = stringResource(R.string.heading_profile_create_species_yes_pet))
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.large))
                PetSpeciesInputSection()
                Spacer(Modifier.weight(1f))
                CommonButton(title = stringResource(DS_R.string.next_button_string)) {
                    onNavigateToPetBio()
                }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
            }
        }
    }
}

@Composable
fun PetSpeciesInputSection() {
    var nameInput by rememberSaveable() {
        mutableStateOf("")
    }

    Text(
        stringResource(R.string.species_dropdown_label_profile_create_species_yes_pet),
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
    )
    Spacer(modifier = Modifier.padding(bottom = LanPetDimensions.Spacing.small))
    TextFieldWithDeleteButton(
        value = nameInput,
        placeholder = stringResource(R.string.placeholder_profile_create_species_input),
    ) {
        nameInput = it
    }

}

@PreviewLightDark
@Composable
fun PreviewProfileCreatePetSpeciesScreen() {
    LanPetAppTheme {
        ProfileCreatePetSpeciesScreen()
    }
}

