package com.lanpet.domain.model

enum class Age(
    val value: String,
    val intValue: Int,
) {
    TENS("10대", 10),
    TWENTIES("20대", 20),
    THIRTIES("30대", 30),
    FORTIES("40대", 40),
    FIFTIES("50대 이상", 50),
    SIXTIES("60대", 60),
    SEVENTIES("70대", 70),
    EIGHTIES("80대", 80),
    NINETIES("90대", 90),
}
