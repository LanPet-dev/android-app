package com.example.navigation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppNavigationViewModel @Inject constructor(
    val navigationManager: NavigationManager
) : ViewModel()