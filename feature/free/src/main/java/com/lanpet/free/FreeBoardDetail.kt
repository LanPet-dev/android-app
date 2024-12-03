package com.lanpet.free

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.loremIpsum
import com.lanpet.core.common.widget.CommonChip
import com.lanpet.core.common.widget.CommonNavigateUpButton
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.designsystem.R
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreeBoardDetail(
    onNavigateUp: () -> Unit,
) {
    val verticalScrollState = rememberScrollState()

    Scaffold(
        topBar = {
            LanPetTopAppBar(
                navigationIcon = {
                    CommonNavigateUpButton {
                        onNavigateUp()
                    }
                },
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            Column(
                modifier = Modifier.scrollable(
                    state = verticalScrollState,
                    orientation = Orientation.Vertical,
                )
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = LanPetDimensions.Spacing.small)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        CommonChip("추천해요")
                        Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
                        Text("강아지", style = MaterialTheme.customTypography().body3RegularSingle)
                    }
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))

                    Text("Head Title", style = MaterialTheme.customTypography().title3SemiBoldMulti)
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_bottom_nav_mypage_unselected),
                            contentDescription = "ic_profile",
                            modifier = Modifier
                                .size(24.dp)
                        )
                        Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxxSmall))
                        Text(
                            "Nickname",
                            style = MaterialTheme.customTypography().body3RegularSingle
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            "2일전", style = MaterialTheme.customTypography().body2RegularSingle.copy(
                                color = GrayColor.Gray300
                            )
                        )
                    }

                }

                //line
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = LanPetDimensions.Spacing.medium)
                        .size(1.dp)
                        .background(GrayColor.Gray50)
                )

                Text(
                    loremIpsum(),
                    style = MaterialTheme.customTypography().body2RegularMulti,
                    modifier = Modifier.padding(horizontal = LanPetDimensions.Spacing.small)
                )

                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))
                Image(
                    painter = painterResource(id = R.drawable.img_dummy),
                    contentDescription = "Profile Image",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )

                //line
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = LanPetDimensions.Spacing.medium)
                        .size(4.dp)
                        .background(GrayColor.Gray50)
                )
                Text(
                    "댓글 0", style = MaterialTheme.customTypography().body2RegularMulti
                )
                //line
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = LanPetDimensions.Spacing.medium)
                        .size(4.dp)
                        .background(GrayColor.Gray50)
                )

            }

        }
    }
}

//TODO: Comment section UI

@PreviewLightDark
@Composable
fun FreeBoardDetailPreview() {
    LanPetAppTheme {
        FreeBoardDetail(
            onNavigateUp = {}
        )
    }
}