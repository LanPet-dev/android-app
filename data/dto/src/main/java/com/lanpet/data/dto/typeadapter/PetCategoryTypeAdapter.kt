package com.lanpet.data.dto.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.lanpet.domain.model.PetCategory

class PetCategoryTypeAdapter : TypeAdapter<PetCategory?>() {
    override fun write(
        out: JsonWriter?,
        value: PetCategory?,
    ) {
        out?.let {
            value?.let { out.value(value.toString()) }
        }
    }

    override fun read(`in`: JsonReader?): PetCategory? =
        `in`?.nextString()?.let {
            PetCategory.valueOf(it)
        }
}
