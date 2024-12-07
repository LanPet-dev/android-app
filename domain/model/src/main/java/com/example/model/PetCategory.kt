package com.example.model

import androidx.annotation.Keep

@Keep
enum class PetCategory(val value: String) {
    DOG("강아지"),
    CAT("고양이"),
    HAMSTER("햄스터"),
    LIZARD("도마뱀"),
    PARROT("앵무새"),
    FISH("물고기"),
    SNAKE("뱀"),
    SPIDER("거미"),
    AMPHIBIAN("양서류"),
    REPTILE("파충류"),
    TURTLE("거북이"),
    ETC("기타"),
}
