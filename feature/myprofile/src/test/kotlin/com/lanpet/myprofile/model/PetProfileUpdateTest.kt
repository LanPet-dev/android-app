package com.lanpet.myprofile.model

import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.profile.Pet
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class PetProfileUpdateTest {
    @Nested
    inner class `Init test` {
        @Test
        fun `When id is empty, then throw exception`() {
            assertThrows<IllegalArgumentException> {
                PetProfileUpdate(
                    id = "",
                    type = null,
                    profileImageUri = null,
                    nickName = null,
                    bio = null,
                    pet = null,
                    shouldCheckNicknameDuplicate = null,
                    nicknameDuplicateChecked = null,
                )
            }
        }

        @Test
        fun `When id is not empty, then not throw exception`() {
            val petProfileUpdate =
                PetProfileUpdate(
                    id = "id",
                    type = null,
                    profileImageUri = null,
                    nickName = null,
                    bio = null,
                    pet = null,
                    shouldCheckNicknameDuplicate = null,
                    nicknameDuplicateChecked = null,
                )

            assertAll(
                { assert(petProfileUpdate.id == "id") },
                { assert(petProfileUpdate.type == null) },
                { assert(petProfileUpdate.profileImageUri == null) },
                { assert(petProfileUpdate.nickName == null) },
                { assert(petProfileUpdate.bio == null) },
                { assert(petProfileUpdate.pet == null) },
                { assert(petProfileUpdate.shouldCheckNicknameDuplicate == null) },
                { assert(petProfileUpdate.nicknameDuplicateChecked == null) },
            )
        }
    }

    @Nested
    inner class `checkValidation test` {
        @Nested
        inner class `fail test` {
            @Test
            fun `When type is null, then return false`() {
                val petProfileUpdate =
                    PetProfileUpdate(
                        id = "id",
                        type = null,
                        profileImageUri = null,
                        nickName = null,
                        bio = null,
                        pet = null,
                        shouldCheckNicknameDuplicate = null,
                        nicknameDuplicateChecked = null,
                    )

                assert(!petProfileUpdate.checkValidation())
            }

            @Test
            fun `When pet is null, then return false`() {
                val petProfileUpdate =
                    PetProfileUpdate(
                        id = "id",
                        type = null,
                        profileImageUri = null,
                        nickName = null,
                        bio = null,
                        pet = null,
                        shouldCheckNicknameDuplicate = null,
                        nicknameDuplicateChecked = null,
                    )

                assert(!petProfileUpdate.checkValidation())
            }

            @Test
            fun `When breed is null, then return false`() {
                val petProfileUpdate =
                    PetProfileUpdate(
                        id = "id",
                        type = null,
                        profileImageUri = null,
                        nickName = null,
                        bio = null,
                        pet =
                            Pet(
                                breed = null,
                                petCategory = PetCategory.CAT,
                                weight = null,
                                birthDate = null,
                            ),
                        shouldCheckNicknameDuplicate = null,
                        nicknameDuplicateChecked = null,
                    )

                assert(!petProfileUpdate.checkValidation())
            }

            @Test
            fun `When nickName is empty, then return false`() {
                val petProfileUpdate =
                    PetProfileUpdate(
                        id = "id",
                        type = null,
                        profileImageUri = null,
                        nickName = "",
                        bio = null,
                        pet =
                            Pet(
                                breed = "breed",
                                petCategory = PetCategory.CAT,
                                weight = null,
                                birthDate = null,
                            ),
                        shouldCheckNicknameDuplicate = null,
                        nicknameDuplicateChecked = null,
                    )

                assert(!petProfileUpdate.checkValidation())
            }

            @Test
            fun `When nickName is less than 2 characters, then return false`() {
                val petProfileUpdate =
                    PetProfileUpdate(
                        id = "id",
                        type = null,
                        profileImageUri = null,
                        nickName = "a",
                        bio = null,
                        pet =
                            Pet(
                                breed = "breed",
                                petCategory = PetCategory.CAT,
                                weight = null,
                                birthDate = null,
                            ),
                        shouldCheckNicknameDuplicate = null,
                        nicknameDuplicateChecked = null,
                    )

                assert(!petProfileUpdate.checkValidation())
            }

            @Test
            fun `When nickName is more than 20 characters, then return false`() {
                val petProfileUpdate =
                    PetProfileUpdate(
                        id = "id",
                        type = null,
                        profileImageUri = null,
                        nickName = "a".repeat(21),
                        bio = null,
                        pet =
                            Pet(
                                breed = "breed",
                                petCategory = PetCategory.CAT,
                                weight = null,
                                birthDate = null,
                            ),
                        shouldCheckNicknameDuplicate = null,
                        nicknameDuplicateChecked = null,
                    )

                assert(!petProfileUpdate.checkValidation())
            }
        }

        @Nested
        inner class `success test` {
            @Test
            fun `When type, pet, breed, nickName are not null, then return true`() {
                val petProfileUpdate =
                    PetProfileUpdate(
                        id = "id",
                        type = ProfileType.PET,
                        profileImageUri = null,
                        nickName = "nickName",
                        bio = "bio",
                        pet =
                            Pet(
                                breed = "breed",
                                petCategory = PetCategory.CAT,
                                weight = null,
                                birthDate = null,
                            ),
                        shouldCheckNicknameDuplicate = null,
                        nicknameDuplicateChecked = null,
                    )

                assert(petProfileUpdate.checkValidation())
            }

            @Test
            fun `When shouldCheckNicknameDuplicate is true and nicknameDuplicateChecked is true, then return true`() {
                val petProfileUpdate =
                    PetProfileUpdate(
                        id = "id",
                        type = ProfileType.PET,
                        profileImageUri = null,
                        nickName = "nickName",
                        bio = "bio",
                        pet =
                            Pet(
                                breed = "breed",
                                petCategory = PetCategory.CAT,
                                weight = null,
                                birthDate = null,
                            ),
                        shouldCheckNicknameDuplicate = true,
                        nicknameDuplicateChecked = true,
                    )

                assert(petProfileUpdate.checkValidation())
            }

            @Test
            fun `When shouldCheckNicknameDuplicate is false, then return true`() {
                val petProfileUpdate =
                    PetProfileUpdate(
                        id = "id",
                        type = ProfileType.PET,
                        profileImageUri = null,
                        nickName = "nickName",
                        bio = "bio",
                        pet =
                            Pet(
                                breed = "breed",
                                petCategory = PetCategory.CAT,
                                weight = null,
                                birthDate = null,
                            ),
                        shouldCheckNicknameDuplicate = false,
                        nicknameDuplicateChecked = null,
                    )

                assert(petProfileUpdate.checkValidation())
            }
        }
    }
}
