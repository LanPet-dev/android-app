package com.lanpet.domain.usecase.profile

import com.lanpet.domain.repository.ProfileRepository
import com.lanpet.domain.repository.S3UploadRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class UploadProfileImageResourceUseCase
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
        private val s3UploadRepository: S3UploadRepository,
    ) {
        @OptIn(FlowPreview::class)
        suspend operator fun invoke(
            profileId: String,
            profileImage: ByteArray,
        ) = profileRepository
            .deleteProfileResource(profileId)
            .flatMapConcat {
                profileRepository
                    .getProfileResourceUploadUrl(profileId)
            }.flatMapConcat { urlItems ->
                s3UploadRepository.uploadImageResource(
                    url = urlItems.items.first(),
                    byteArray = profileImage,
                )
            }.onEach { delay(2000) }
    }