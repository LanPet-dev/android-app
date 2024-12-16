package com.lanpet.data.dto

import com.lanpet.core.common.removeLineBreak
import com.lanpet.core.common.removeWhiteSpace
import com.lanpet.domain.model.PetCategory
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RegisterPetProfileRequestTest {
    @Test
    fun `RegisterPetProfileRequest serializing test1`() {
        val registerPetProfileRequest =
            RegisterPetProfileRequest(
                nickname = "nickname",
                pet =
                    PetDto(
                        petType = PetCategory.CAT,
                        feature = listOf("cute", "lovely"),
                        birthDate = "2021-01-01",
                        weight = 3.5,
                    ),
            )

        val registerPetProfileRequestJsonString =
            """
                {
                    "profileType": "PET",
                    "nickname": "nickname",
                    "pictureUrl": null,
                    "introduction": null,
                    "pet": {
                        "petType": "CAT",
                        "breed": null,
                        "feature": ["cute", "lovely"],
                        "birthDate": "2021-01-01",
                        "weight": 3.5
                    }
                }
                """.removeWhiteSpace().removeLineBreak()

        val json =
            Json {
                encodeDefaults = true
            }

        val serialized =
            json.encodeToString(RegisterPetProfileRequest.serializer(), registerPetProfileRequest)
        val deserialized = json.decodeFromString(RegisterPetProfileRequest.serializer(), serialized)

        assertEquals(registerPetProfileRequest, deserialized)
        assertEquals(registerPetProfileRequestJsonString, serialized)
    }

    @Test
    fun `RegisterPetProfileRequest serializing test2`() {
        val registerPetProfileRequest =
            RegisterPetProfileRequest(
                nickname = "nickname",
                pet =
                    PetDto(
                        petType = PetCategory.AMPHIBIAN,
                        feature = emptyList(),
                    ),
            )

        val registerPetProfileRequestJsonString =
            """
                {
                    "profileType": "PET",
                    "nickname": "nickname",
                    "pictureUrl": null,
                    "introduction": null,
                    "pet": {
                        "petType": "AMPHIBIAN",
                        "breed": null,
                        "feature": [],
                        "birthDate": null,
                        "weight":null 
                    }
                }
                """.removeWhiteSpace().removeLineBreak()

        val json =
            Json {
                encodeDefaults = true
            }

        val serialized =
            json.encodeToString(RegisterPetProfileRequest.serializer(), registerPetProfileRequest)
        val deserialized = json.decodeFromString(RegisterPetProfileRequest.serializer(), serialized)

        assertEquals(registerPetProfileRequest, deserialized)
        assertEquals(registerPetProfileRequestJsonString, serialized)
    }
}
