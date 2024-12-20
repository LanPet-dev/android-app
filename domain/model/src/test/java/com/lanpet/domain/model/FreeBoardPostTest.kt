package com.lanpet.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FreeBoardPostTest {
    @Test
    fun testPost() {
        val freeBoardPost =
            FreeBoardPost(
                petCategory = PetCategory.DOG,
                title = "title",
                tags = listOf("tag1", "tag2"),
                content = "content",
                images = listOf("image1", "image2"),
                createdAt = "2016-10-27T17:13:40+00:00",
                likeCount = 1,
                commentCount = 10,
                id = 1,
                updatedAt = "2016-11-24T17:13:40+00:00",
            )
        assertEquals(freeBoardPost.createdAtKorString, "2016-10-28 02:13:40")
    }
}
