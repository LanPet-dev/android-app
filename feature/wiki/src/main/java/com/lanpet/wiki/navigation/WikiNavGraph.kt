package com.lanpet.wiki.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lanpet.wiki.WikiScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.wikiNavGraph() {
    navigation<WikiBaseRoute>(
        startDestination = Wiki,
    ) {
        composable<Wiki> {
            WikiScreen()
        }
    }
}

fun NavController.navigateToWikiScreen() {
    navigate(
        Wiki,
    ) {
        popUpTo(0) {
            inclusive = true
        }
    }
}

@Serializable
object WikiBaseRoute

@Serializable
object Wiki