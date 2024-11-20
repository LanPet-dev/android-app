package com.example.profile.screen.yespet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.LanPetSpacing
import com.example.designsystem.theme.baseHorizontalMargin
import com.example.designsystem.theme.widgets.CommonButton
import com.example.designsystem.theme.widgets.LanPetTopAppBar
import com.example.designsystem.theme.widgets.TextFieldWithDeleteButton
import com.example.profile.R
import com.example.profile.widget.Heading
import com.example.profile.widget.HeadingHint
import com.example.designsystem.R as DS_R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreateYesPetNameScreen() {
    Scaffold(
        topBar = {
            LanPetTopAppBar(
                title = {
                    Text(stringResource(R.string.appbar_title_profile_create))
                },
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = MaterialTheme.baseHorizontalMargin())
        ) {
            Spacer(Modifier.padding(LanPetSpacing.xLarge))
            Heading(title = stringResource(R.string.heading_profile_create_yes_pet_name))
            Spacer(Modifier.padding(LanPetSpacing.xxSmall))
            HeadingHint(title = stringResource(R.string.sub_heading_profile_create_yes_pet_name))
            Spacer(Modifier.padding(LanPetSpacing.xLarge))
            Image(
                painter = painterResource(DS_R.drawable.dummy),
                contentDescription = null,
                modifier = Modifier
                    .size(110.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .clip(
                        shape = CircleShape
                    )
            )
            Spacer(Modifier.padding(LanPetSpacing.xLarge))
            PetNameInputSection()
            Spacer(Modifier.weight(1f))
            CommonButton(title = stringResource(DS_R.string.next_button_string)) {}
        }

    }
}

@Composable
fun PetNameInputSection() {
    var nameInput by rememberSaveable() {
        mutableStateOf("")
    }

    Text(
        stringResource(R.string.name_input_label_profile_create_yes_pet_name),
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
    )
    Spacer(modifier = Modifier.padding(bottom = LanPetSpacing.small))
    TextFieldWithDeleteButton(
        value = nameInput,
        placeholder = stringResource(R.string.name_input_placeholder_profile_create_yes_pet_name),
    ) {
        nameInput = it
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewProfileCreateYesPetNameScreen() {
    ProfileCreateYesPetNameScreen()
}