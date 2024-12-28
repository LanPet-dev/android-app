package com.lanpet.myprofile.viewmodel

import androidx.lifecycle.ViewModel
import com.lanpet.domain.usecase.profile.ModifyPetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ManagePetProfileViewModel
    @Inject
    constructor(
        private val modifyPetProfileUseCase: ModifyPetProfileUseCase,
    ) : ViewModel() {
        init {
            Timber.d("ManagePetProfileViewModel created")
        }
    }
