package com.lanpet.core.common

class FormValidator<T>(
    val validate: (T) -> FormValidationStatus,
)

sealed class FormValidationStatus : Cloneable {
    data class Invalid(
        val message: String? = null,
    ) : FormValidationStatus()

    data class Valid(
        val message: String? = null,
    ) : FormValidationStatus()

    data class Initial(
        val message: String? = null,
    ) : FormValidationStatus()
}
