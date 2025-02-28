package com.lanpet.feature.landing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.domain.repository.LandingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel
    @Inject
    constructor(
        private val landingRepository: LandingRepository,
    ) : ViewModel() {
        fun saveShouldShowLanding(shouldShowLanding: Boolean) {
            viewModelScope.launch {
                landingRepository.saveShouldShowLanding(shouldShowLanding)
            }
        }
    }
