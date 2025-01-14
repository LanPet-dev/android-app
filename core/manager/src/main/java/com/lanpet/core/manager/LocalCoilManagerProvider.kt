package com.lanpet.core.manager

import androidx.compose.runtime.staticCompositionLocalOf

val LocalCoilManager =
    staticCompositionLocalOf<CoilManager> { error("No CoilManager provided") }
