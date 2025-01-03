package com.lanpet.myprofile.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.core.auth.AuthManager
import com.lanpet.domain.usecase.profile.DeleteProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ProfileListViewModel
    @Inject
    constructor(
        private val deleteProfileUseCase: DeleteProfileUseCase,
        private val authManager: AuthManager,
    ) : ViewModel() {
        private val _event = MutableSharedFlow<ProfileListEvent>()

        @OptIn(FlowPreview::class)
        fun deleteProfile(profileId: String) {
            viewModelScope.launch {
                try {
                    deleteProfileUseCase(profileId).timeout(5.seconds).first()
                    _event.emit(ProfileListEvent.DeleteProfileSuccess)
                    authManager.getProfiles()
                } catch (e: Exception) {
                    Timber.e(e)
                    _event.emit(ProfileListEvent.DeleteProfileFail)
                }
            }
        }
    }

@Stable
sealed class ProfileListEvent {
    data object DeleteProfileSuccess : ProfileListEvent()

    data object DeleteProfileFail : ProfileListEvent()
}
