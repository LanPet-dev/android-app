package com.lanpet.domain.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class Profile(
    val nickname: String,
    @Serializable(with = Base64StringSerializer::class)
    val profileImage: String?,
)

object Base64StringSerializer : KSerializer<String> {
    override val descriptor: SerialDescriptor
        get() = String.serializer().descriptor

    override fun serialize(
        encoder: Encoder,
        value: String,
    ) {
        val base64Encoded =
            android.util.Base64.encode(value.toByteArray(), android.util.Base64.URL_SAFE)
        encoder.encodeString(String(base64Encoded))
    }

    override fun deserialize(decoder: Decoder): String {
        val base64Decoded =
            android.util.Base64.decode(decoder.decodeString(), android.util.Base64.URL_SAFE)
        return String(base64Decoded)
    }
}
