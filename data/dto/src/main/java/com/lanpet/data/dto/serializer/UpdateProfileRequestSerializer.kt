package com.lanpet.data.dto.serializer

import com.lanpet.data.dto.ButlerDto
import com.lanpet.data.dto.PetDto
import com.lanpet.data.dto.UpdateProfileRequest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

object UpdateProfileRequestSerializer : KSerializer<UpdateProfileRequest> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("UpdateProfileRequest") {
            element("nickname", String.serializer().nullable.descriptor)
            element("pictureUrl", String.serializer().nullable.descriptor)
            element("introduction", String.serializer().nullable.descriptor)
            element("pet", PetDto.serializer().nullable.descriptor)
            element("butler", ButlerDto.serializer().nullable.descriptor)
        }

    override fun serialize(
        encoder: Encoder,
        value: UpdateProfileRequest,
    ) {
        encoder.encodeStructure(descriptor) {
            value.nickname?.let { encodeStringElement(descriptor, 0, it) }
            value.pictureUrl?.let { encodeStringElement(descriptor, 1, it) }
            value.introduction?.let { encodeStringElement(descriptor, 2, it) }
            value.pet?.let {
                encodeSerializableElement(descriptor, 3, PetDto.serializer(), it)
            }
            value.butler?.let {
                encodeSerializableElement(descriptor, 4, ButlerDto.serializer(), it)
            }
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): UpdateProfileRequest =
        decoder.decodeStructure(descriptor) {
            var nickname: String? = null
            var pictureUrl: String? = null
            var introduction: String? = null
            var pet: PetDto? = null
            var butler: ButlerDto? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    -1 -> break
                    0 ->
                        nickname =
                            decodeNullableSerializableElement(
                                descriptor,
                                0,
                                String.serializer().nullable,
                            )

                    1 ->
                        pictureUrl =
                            decodeNullableSerializableElement(
                                descriptor,
                                1,
                                String.serializer().nullable,
                            )

                    2 ->
                        introduction =
                            decodeNullableSerializableElement(
                                descriptor,
                                2,
                                String.serializer().nullable,
                            )

                    3 ->
                        pet =
                            decodeNullableSerializableElement(
                                descriptor,
                                3,
                                PetDto.serializer().nullable,
                            )

                    4 ->
                        butler =
                            decodeNullableSerializableElement(
                                descriptor,
                                4,
                                ButlerDto.serializer().nullable,
                            )

                    else -> throw SerializationException("Unexpected index $index")
                }
            }

            UpdateProfileRequest(
                nickname = nickname,
                pictureUrl = pictureUrl,
                introduction = introduction,
                pet = pet,
                butler = butler,
            )
        }
}
