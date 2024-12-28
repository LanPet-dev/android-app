package com.lanpet.data.dto.serializer

import com.lanpet.data.dto.UpdateProfileRequest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

object UpdateProfileRequestSerializer : KSerializer<UpdateProfileRequest> {
    private val json =
        Json {
            encodeDefaults = false
            explicitNulls = false
        }

    private val delegateSerializer = UpdateProfileRequest.serializer()

    override val descriptor = delegateSerializer.descriptor

    override fun serialize(
        encoder: Encoder,
        value: UpdateProfileRequest,
    ) {
        val jsonElement = json.encodeToJsonElement(delegateSerializer, value)
        encoder.encodeSerializableValue(JsonElement.serializer(), jsonElement)
    }

    override fun deserialize(decoder: Decoder): UpdateProfileRequest {
        val element = decoder.decodeSerializableValue(JsonElement.serializer())
        return json.decodeFromJsonElement(delegateSerializer, element)
    }
}
