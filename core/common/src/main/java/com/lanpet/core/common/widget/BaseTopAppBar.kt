package com.lanpet.core.common.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.lanpet.core.designsystem.theme.LanPetAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanPetTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors =
        TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.background,
        ),
) {
    TopAppBar(
        windowInsets = WindowInsets(0, 0, 0, 0),
        title = title,
        navigationIcon = { navigationIcon?.invoke() },
        actions = actions,
        colors = colors,
        modifier = modifier.systemBarsPadding(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanPetCloseableTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit = {},
) {
    LanPetTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
        },
        actions = {
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                )
            }
        },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanPetCenterAlignedTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors =
        TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.background,
        ),
) {
    CenterAlignedTopAppBar(
        windowInsets = WindowInsets(0, 0, 0, 0),
        title = title,
        navigationIcon = { navigationIcon?.invoke() },
        actions = actions,
        colors = colors,
        modifier = modifier.systemBarsPadding(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun LanPetTopAppBarPreview() {
    LanPetAppTheme {
        Column {
            LanPetTopAppBar(
                title = { Text("Title") },
            )
            LanPetTopAppBar(
                navigationIcon = {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Close",
                    )
                },
                title = { Text("Title") },
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun LanPetTopAppBarWithBackPreview() {
    LanPetAppTheme {
        LanPetCloseableTopAppBar(
            title = "Title",
        ) {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun LanPetCenterAlignedTopAppBarPreview() {
    LanPetAppTheme {
        Column {
            LanPetCenterAlignedTopAppBar(
                title = { Text("Title") },
            )
            LanPetCenterAlignedTopAppBar(
                navigationIcon = {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Close",
                    )
                },
                title = { Text("Title") },
            )
        }
    }
}
