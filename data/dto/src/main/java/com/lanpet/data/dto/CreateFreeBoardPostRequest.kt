package com.lanpet.data.dto

import com.lanpet.domain.model.FreeBoardCategoryType
import com.lanpet.domain.model.FreeBoardPostCreate
import com.lanpet.domain.model.PetCategory
import kotlinx.serialization.Serializable

@Serializable
data class CreateFreeBoardPostRequest(
    val profileId: String,
    val category: FreeBoardCategoryType,
    val petType: PetCategory,
    val title: String,
    val content: String,
) {
    companion object {
        @JvmStatic
        fun fromDomainToCreateRequest(freeBoardPostCreate: FreeBoardPostCreate): CreateFreeBoardPostRequest =
            CreateFreeBoardPostRequest(
                profileId = freeBoardPostCreate.profileId,
                category = freeBoardPostCreate.boardCategory,
                petType = freeBoardPostCreate.petCategory,
                title = freeBoardPostCreate.title,
                content = freeBoardPostCreate.body,
            )
    }
}
