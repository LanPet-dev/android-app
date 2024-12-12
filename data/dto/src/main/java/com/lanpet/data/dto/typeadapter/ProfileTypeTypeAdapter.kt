package com.lanpet.data.dto.typeadapter

import com.lanpet.domain.model.ProfileType
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class ProfileTypeTypeAdapter : TypeAdapter<ProfileType>() {
    override fun write(out: JsonWriter?, value: ProfileType?) {
        out?.let {
            value?.let { out.value(value.value) }
        }
    }

    override fun read(`in`: JsonReader?): ProfileType? {
        return `in`?.nextString()?.let { ProfileType.fromValue(it) }
    }
}