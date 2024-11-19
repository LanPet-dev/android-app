package com.example.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

interface NavigationCommand {
    val route: String
    val args:  List<NamedNavArgument> get() = emptyList<NamedNavArgument>()
    val deepLinks: List<NavDeepLink> get() = emptyList<NavDeepLink>()
}

