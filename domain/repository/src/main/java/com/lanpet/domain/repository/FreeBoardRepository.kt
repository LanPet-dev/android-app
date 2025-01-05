package com.lanpet.domain.repository

import com.lanpet.domain.model.FreeBoardComment
import com.lanpet.domain.model.FreeBoardPost
import com.lanpet.domain.model.FreeBoardPostCreate
import com.lanpet.domain.model.FreeBoardPostDetail
import kotlinx.coroutines.flow.Flow

interface FreeBoardRepository {
    fun getFreeBoardPostList(): Flow<List<FreeBoardPost>>

    fun getFreeBoardDetail(id: String): Flow<FreeBoardPostDetail>

    fun getFreeBoardCommentList(id: String): Flow<List<FreeBoardComment>>

    fun createFreeBoardPost(freeBoardPostCreate: FreeBoardPostCreate): Flow<String>
}
