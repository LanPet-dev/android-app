package com.lanpet.data.dto.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.lanpet.domain.model.AuthorityType

class AuthorityTypeTypeAdapter : TypeAdapter<AuthorityType>() {
    override fun write(
        out: JsonWriter?,
        value: AuthorityType?,
    ) {
        out?.let {
            value?.let { out.value(value.name) }
        }
    }

    override fun read(`in`: JsonReader?): AuthorityType? = `in`?.nextString()?.let { AuthorityType.valueOf(it) }
}
