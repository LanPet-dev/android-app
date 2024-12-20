package com.lanpet.data.dto

import com.lanpet.core.common.removeLineBreak
import com.lanpet.core.common.removeWhiteSpace
import com.lanpet.domain.model.PetCategory
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PetDtoTest {
    @Test
    fun `Serializing test1`() {
        // Given
        val petDto =
            PetDto(
                petType = PetCategory.CAT,
                breed = "낭만고양이",
                feature =
                    listOf(
                        "cute",
                        "lovely",
                    ),
                birthDate = "2021-01-01",
                weight = 3.5,
            )

        val petDtoJsonString =
            """
                {
                    "petType": "CAT",
                    "breed": "낭만고양이",
                    "feature": ["cute", "lovely"],
                    "birthDate": "2021-01-01",
                    "weight": 3.5
                }
                """.removeWhiteSpace().removeLineBreak()

        val json =
            Json {
                encodeDefaults = true
            }

        // When
        val serialized = json.encodeToString(PetDto.serializer(), petDto)
        val deserialized = json.decodeFromString(PetDto.serializer(), serialized)

        // Then
        assertEquals(petDto, deserialized)
        assertEquals(petDtoJsonString, serialized)
    }

    @Test
    fun `Serializing test2`() {
        // Given
        val petDto =
            PetDto(
                petType = PetCategory.LIZARD,
                // empty list
                feature = emptyList(),
                birthDate = "2021-01-01",
                weight = 3.5,
            )

        val petDtoJsonString =
            """
                {
                    "petType": "LIZARD",
                    "breed": null,
                    "feature": [],
                    "birthDate": "2021-01-01",
                    "weight": 3.5
                }
                """.removeWhiteSpace().removeLineBreak()

        val json =
            Json {
                encodeDefaults = true
            }

        // When
        val serialized = json.encodeToString(PetDto.serializer(), petDto)
        val deserialized = json.decodeFromString(PetDto.serializer(), serialized)

        // Then
        assertEquals(petDto, deserialized)
        assertEquals(petDtoJsonString, serialized)
    }
}
