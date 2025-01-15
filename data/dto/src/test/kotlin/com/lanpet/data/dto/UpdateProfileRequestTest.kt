package com.lanpet.data.dto

import com.lanpet.core.common.removeWhiteSpace
import com.lanpet.domain.model.PetCategory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test

class UpdateProfileRequestTest {
    @Test
    fun `Serializing test1`() {
        val updateProfileRequest =
            UpdateProfileRequest(
                pet =
                    PetDto(
                        petType = PetCategory.CAT,
                    ),
            )

        // when
        val res = Json.encodeToString(updateProfileRequest).removeWhiteSpace().removeWhiteSpace()

        assert(res == "{\"pet\":{\"petType\":\"CAT\"}}")
    }

    @Test
    fun `Serializing test2`() {
        val updateProfileRequest =
            UpdateProfileRequest(
                nickname = "nickname",
            )

        // when
        val res = Json.encodeToString(updateProfileRequest).removeWhiteSpace().removeWhiteSpace()

        assert(res == "{\"nickname\":\"nickname\"}")
    }
}
