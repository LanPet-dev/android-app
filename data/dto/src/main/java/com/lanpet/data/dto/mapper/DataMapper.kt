package com.lanpet.data.dto.mapper

import com.lanpet.data.dto.ButlerDto
import com.lanpet.data.dto.PetDto
import com.lanpet.domain.model.Age
import com.lanpet.domain.model.profile.Butler
import com.lanpet.domain.model.profile.Pet

interface Mapper<I, O> {
    fun map(input: I): O
}

abstract class MapperRegistry {
    val mappers = mutableMapOf<Pair<Class<*>, Class<*>>, Mapper<*, *>>()

    abstract fun <I, O> getMapper(
        input: Class<I>,
        output: Class<O>,
    ): Mapper<I, O>?

    inline fun <reified I, reified O> register(mapper: Mapper<I, O>) {
        mappers[Pair(I::class.java, O::class.java)] = mapper
    }
}

class DefaultMapperRegistry : MapperRegistry() {
    @Suppress("UNCHECKED_CAST")
    override fun <I, O> getMapper(
        input: Class<I>,
        output: Class<O>,
    ): Mapper<I, O>? = mappers[Pair(input, output)] as? Mapper<I, O>
}

inline fun <reified I, reified O> MapperRegistry.map(input: I): O {
    val mapper = getMapper(I::class.java, O::class.java)
    return mapper?.map(input) ?: throw IllegalArgumentException("No mapper found")
}

class PetDtoToDomainPetMapper : Mapper<PetDto, Pet> {
    override fun map(input: PetDto): Pet =
        Pet(
            petCategory = input.petType,
            breed = input.breed,
            feature = input.feature?.split(",") ?: emptyList(),
            weight = input.weight,
            birthDate = input.birthDate,
        )
}

class DomainPetToPetDtoMapper : Mapper<Pet, PetDto> {
    override fun map(input: Pet): PetDto =
        PetDto(
            petType = input.petCategory,
            breed = input.breed,
            feature = input.feature?.joinToString(","),
            weight = input.weight,
            birthDate = input.birthDate,
        )
}

class ButlerDtoToDomainButlerMapper : Mapper<ButlerDto, Butler> {
    override fun map(input: ButlerDto): Butler =
        Butler(
            age = Age.entries.firstOrNull { it.intValue == input.ageRange } ?: Age.NONE,
            preferredPet = input.preferredPet,
        )
}

class DomainButlerToButlerDtoMapper : Mapper<Butler, ButlerDto> {
    override fun map(input: Butler): ButlerDto =
        ButlerDto(
            ageRange = input.age.intValue,
            preferredPet = input.preferredPet,
        )
}
