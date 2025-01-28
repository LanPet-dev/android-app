package com.lanpet.domain.model.free

import com.lanpet.domain.model.Profile
import kotlinx.serialization.Serializable
import java.util.SortedSet
import java.util.TreeSet

@Serializable
data class FreeBoardComment(
    val id: String,
    val profile: Profile,
    val comment: String?,
    val createdAt: String,
    val subComments: List<FreeBoardSubComment> = emptyList(),
) {
    val subCommentsTree: SortedSet<FreeBoardSubComment>
        get() = TreeSet(subComments).toSortedSet(compareBy { it.createdAt })
}

@Serializable
data class FreeBoardSubComment(
    val id: String,
    val profile: Profile,
    val comment: String?,
    val createdAt: String?,
)
