package com.example.dto.typeadapter

import com.example.model.AuthorityType
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class AuthorityTypeTypeAdapter : TypeAdapter<AuthorityType>() {
    override fun write(out: JsonWriter?, value: AuthorityType?) {
        out?.let {
            value?.let { out.value(value.name) }
        }
    }

    override fun read(`in`: JsonReader?): AuthorityType? {
        return `in`?.nextString()?.let { AuthorityType.valueOf(it) }
    }
}