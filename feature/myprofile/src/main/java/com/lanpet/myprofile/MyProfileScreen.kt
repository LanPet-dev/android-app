package com.lanpet.myprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.crop
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.core.designsystem.theme.customTypography

@Composable
fun MyProfileScreen() {
    Scaffold(
        topBar = {
            Text(
                style = MaterialTheme.customTypography().title2SemiBoldSingle,
                text = stringResource(R.string.title_appbar_my_profile),
                modifier = Modifier.padding(
                    horizontal = LanPetDimensions.Margin.Layout.horizontal,
                )
            )
        }
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                MyProfileCard()
                Spacer(
                    modifier = Modifier
                        .padding(bottom = LanPetDimensions.Spacing.xLarge)
                        .fillMaxWidth()
                        .size(LanPetDimensions.Spacing.xxSmall)
                        .background(
                            color = MaterialTheme.customColorScheme.spacerLine
                        ),
                )
                Text(
                    style = MaterialTheme.customTypography().title2SemiBoldSingle,
                    text = stringResource(R.string.sub_title_activity_list_my_profile),
                    modifier = Modifier.padding(
                        horizontal = LanPetDimensions.Margin.Layout.horizontal,
                    )
                )
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.small))
                Column {
                    ActivityListItem(
                        leadingContent = {
                            Image(
                                painter = painterResource(id = com.lanpet.core.designsystem.R.drawable.ic_file),
                                contentDescription = "Profile",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        headlineContentText = "My Profile",
                        onclick = { }
                    )
                    ActivityListItem(
                        leadingContent = {
                            Image(
                                painter = painterResource(id = com.lanpet.core.designsystem.R.drawable.ic_message),
                                contentDescription = "Profile",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        headlineContentText = "My Profile",
                        onclick = { }
                    )
                    ActivityListItem(
                        leadingContent = {
                            Image(
                                painter = painterResource(id = com.lanpet.core.designsystem.R.drawable.ic_bookmark),
                                contentDescription = "Profile",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        headlineContentText = "My Profile",
                        onclick = { }
                    )
                }
            }
        }

    }
}

@Composable
private fun MyProfileCard() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                horizontal = LanPetDimensions.Margin.Layout.horizontal,
                vertical = LanPetDimensions.Margin.xxLarge
            )
    ) {
        Image(
            modifier = Modifier.crop(
                size = 88.dp
            ),
            painter = painterResource(id = com.lanpet.core.designsystem.R.drawable.ic_file),
            contentDescription = "Profile Picture"
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.small))
        Column {
            Text(
                style = MaterialTheme.customTypography().title2SemiBoldSingle,
                text = "John Doe",
            )
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
            Text(
                style = MaterialTheme.customTypography().body1RegularSingle,
                text = "Hello I am John Doe",
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = com.lanpet.core.designsystem.R.drawable.ic_arrow_right),
            modifier = Modifier
                .size(16.dp)
                .clickable { },
            contentDescription = "Arrow Right"
        )
    }
}

@Composable
fun ActivityListItem(
    leadingContent: @Composable () -> Unit?,
    headlineContentText: String,
    trailingContent: @Composable () -> Unit =
        {
            Image(
                painter = painterResource(id = com.lanpet.core.designsystem.R.drawable.ic_arrow_right),
                contentDescription = "Arrow Right",
                modifier = Modifier.size(16.dp)
            )
        },
    onclick: () -> Unit,
) {
    ListItem(
        leadingContent = { leadingContent() },
        headlineContent = {
            Text(
                style = MaterialTheme.customTypography().body2MediumSingle,
                text = headlineContentText,
            )
        },
        trailingContent = {
            trailingContent()
        },
        modifier = Modifier.clickable(onClick = onclick)
    )
}

@Composable
@PreviewLightDark()
fun MyProfileCardPreview() {
    LanPetAppTheme {
        Surface {
            MyProfileCard()
        }
    }
}


@Composable
@PreviewLightDark()
fun MyProfileScreenPreview() {
    LanPetAppTheme {
        MyProfileScreen()
    }
}