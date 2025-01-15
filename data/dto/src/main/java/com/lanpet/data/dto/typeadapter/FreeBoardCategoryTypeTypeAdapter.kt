package com.lanpet.data.dto.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.lanpet.domain.model.free.FreeBoardCategoryType

class FreeBoardCategoryTypeTypeAdapter : TypeAdapter<FreeBoardCategoryType>() {
    override fun write(
        out: JsonWriter?,
        value: FreeBoardCategoryType?,
    ) {
        out?.let {
            value?.let {
                out.value(value.value)
            }
        }
    }

    override fun read(`in`: JsonReader?): FreeBoardCategoryType? = FreeBoardCategoryType.fromValue(`in`?.nextString())
}
