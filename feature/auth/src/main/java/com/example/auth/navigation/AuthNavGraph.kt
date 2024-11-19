package com.example.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.example.auth.LoginScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.authNavGraph(
) {
    composable<Login> {
        LoginScreen()
    }
}

fun NavController.navigateToLoginScreen(){
    this.navigate(Login, navOptions {
        popUpToId
    })
}

@Serializable
object Login

