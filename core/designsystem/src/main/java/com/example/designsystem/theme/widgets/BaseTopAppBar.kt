package com.example.designsystem.theme.widgets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.designsystem.theme.WhiteColor

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
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanPetTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LanPetTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanPetCloseableTopAppBar(
    title: String,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
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

// Preview
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun LanPetTopAppBarPreview() {
    LanPetTopAppBar(
        title = { Text("Title") }
    )
}

@Preview
@Composable
private fun LanPetTopAppBarWithBackPreview() {
    LanPetTopAppBar(
        title = "Title",
        onBackClick = {}
    )
}