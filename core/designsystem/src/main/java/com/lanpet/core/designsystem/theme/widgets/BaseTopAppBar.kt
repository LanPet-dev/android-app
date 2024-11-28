package com.lanpet.core.designsystem.theme.widgets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors().copy(
        containerColor = MaterialTheme.colorScheme.background,
    ),
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = title,
        navigationIcon = { navigationIcon },
        actions = actions,
        colors = colors,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanPetCloseableTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
) {
    LanPetTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        actions = {
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun LanPetTopAppBarPreview() {
    LanPetAppTheme {
        LanPetTopAppBar(
            title = { Text("Title") }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun LanPetTopAppBarWithBackPreview() {
    LanPetAppTheme {
        LanPetCloseableTopAppBar(
            title = "Title",
        ) {}
    }
}