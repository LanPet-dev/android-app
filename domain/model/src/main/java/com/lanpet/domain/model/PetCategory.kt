package com.lanpet.domain.model

enum class PetCategory(
    val value: String,
) {
    DOG("강아지"),
    CAT("고양이"),
    HAMSTER("햄스터"),
    LIZARD("도마뱀"),
    PARROT("앵무새"),
    FISH("물고기"),
    SNAKE("뱀"),
    TURTLE("거북이"),
    OTHER("기타"),
    NONE("없음"),
}
