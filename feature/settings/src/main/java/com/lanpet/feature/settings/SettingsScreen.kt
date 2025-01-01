package com.lanpet.feature.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.auth.LocalAuthManager
import com.lanpet.core.common.MyIconPack
import com.lanpet.core.common.myiconpack.ArrowLeft
import com.lanpet.core.common.myiconpack.ArrowRight
import com.lanpet.core.common.widget.CommonChip
import com.lanpet.core.common.widget.CommonIconButtonBox
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.core.designsystem.theme.customTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: (() -> Unit)? = null,
    onNavigateToMemberLeave: () -> Unit = {},
    onOpenLogoutDialog: (() -> Unit)? = null,
) {
    val authManager = LocalAuthManager.current

    Scaffold(
        topBar = {
            LanPetTopAppBar(
                navigationIcon = {
                    if (onNavigateUp != null) {
                        CommonIconButtonBox(
                            content = {
                                Image(
                                    imageVector = MyIconPack.ArrowLeft,
                                    contentDescription = "ic_arrow_left",
                                    modifier = Modifier.size(28.dp),
                                    colorFilter = ColorFilter.tint(MaterialTheme.customColorScheme.defaultIconColor),
                                )
                            },
                            onClick = { onNavigateUp() },
                        )
                    }
                },
                title = {
                    Text(
                        stringResource(R.string.title_appbar_settings),
                    )
                },
            )
        },
    ) { paddings ->
        Surface(
            modifier = Modifier.padding(paddings),
        ) {
            Column {
                SettingsBaseSection(
                    sectionLabel = stringResource(R.string.sns_label_settings),
                ) {
                    ListItem(
                        headlineContent = {
                            Text(
                                "HeadLine1",
                                style = MaterialTheme.customTypography().body2RegularSingle,
                            )
                        },
                        trailingContent = {
                            Box(
                                modifier =
                                    Modifier
                                        .clip(
                                            shape = CircleShape,
                                        ).clickable {
//                                            authViewModel.logout()
                                            onOpenLogoutDialog?.invoke()
                                        },
                            ) {
                                CommonChip(stringResource(R.string.button_logout))
                            }
                        },
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                SettingsBaseSection(
                    sectionLabel = stringResource(R.string.notification_label_settings),
                ) {
                    // TODO("Satoshi"): separate switch values
                    ListItem(
                        headlineContent = {
                            Text(
                                stringResource(R.string.title_notification_option_switch_settings),
                                style = MaterialTheme.customTypography().body2RegularSingle,
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = true,
                                onCheckedChange = { },
                            )
                        },
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                SettingsBaseSection(
                    sectionLabel = stringResource(R.string.tos_label_settings),
                ) {
                    Column {
                        ListItem(
                            modifier = Modifier.clickable { },
                            headlineContent = {
                                Text(
                                    "서비스 이용약관",
                                    style = MaterialTheme.customTypography().body2RegularSingle,
                                )
                            },
                            trailingContent = {
                                Image(
                                    imageVector = MyIconPack.ArrowRight,
                                    contentDescription = "ic_arrow_right",
                                )
                            },
                        )
                        ListItem(
                            modifier = Modifier.clickable { },
                            headlineContent = {
                                Text(
                                    "개인정보처리방침",
                                    style = MaterialTheme.customTypography().body2RegularSingle,
                                )
                            },
                            trailingContent = {
                                Image(
                                    imageVector = MyIconPack.ArrowRight,
                                    contentDescription = "ic_arrow_right",
                                )
                            },
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                SettingsBaseSection(
                    sectionLabel = stringResource(R.string.information_label_settings),
                ) {
                    ListItem(
                        headlineContent = {
                            Text(
                                "버전 0.01 alpha",
                                style = MaterialTheme.customTypography().body2RegularSingle,
                            )
                        },
                        trailingContent = {
                            Text(
                                "최신버전이에요",
                                style =
                                    MaterialTheme.customTypography().body3SemiBoldSingle.copy(
                                        color = GrayColor.Gray400,
                                    ),
                            )
                        },
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = onNavigateToMemberLeave) {
                    Text(
                        stringResource(R.string.title_leave_member),
                        style =
                            MaterialTheme.customTypography().body3RegularSingle.copy(
                                color = GrayColor.Gray400,
                            ),
                    )
                }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.large))
            }
        }
    }
}

@Composable
fun SettingsBaseSection(
    sectionLabel: String,
    modifier: Modifier = Modifier,
    contents: @Composable () -> Unit,
) {
    Column {
        Text(
            sectionLabel,
            style = MaterialTheme.customTypography().body3SemiBoldSingle.copy(color = GrayColor.Gray400),
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        contents()
    }
}

@Composable
@PreviewLightDark
private fun SettingsBaseSectionPreview() {
    LanPetAppTheme {
        SettingsBaseSection("Section Label") {
            Column {
                ListItem(
                    headlineContent = {
                        Text("HeadLine1")
                    },
                    trailingContent = {
                        Image(
                            imageVector = MyIconPack.ArrowRight,
                            contentDescription = "ic_arrow_right",
                        )
                    },
                )
                ListItem(
                    headlineContent = {
                        Text("HeadLine2")
                    },
                    trailingContent = {
                        Image(
                            imageVector = MyIconPack.ArrowRight,
                            contentDescription = "ic_arrow_right",
                        )
                    },
                )
                ListItem(
                    headlineContent = {
                        Text("HeadLine3")
                    },
                    trailingContent = {
                        Image(
                            imageVector = MyIconPack.ArrowRight,
                            contentDescription = "ic_arrow_right",
                        )
                    },
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun SettingsScreenPreview() {
    LanPetAppTheme {
        SettingsScreen()
    }
}
