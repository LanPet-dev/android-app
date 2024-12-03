package com.example.model

import junit.framework.TestCase.assertEquals
import org.junit.Test

class PostTest {

    @Test
    fun testPost() {
        val post = Post(
            petCategory = PetCategory.DOG,
            title = "title",
            tags = listOf("tag1", "tag2"),
            content = "content",
            images = listOf("image1", "image2"),
            createdAt = "2016-10-27T17:13:40+00:00",
            likeCount = 1,
            commentCount = 10,
        )

        assertEquals(post.createdAtKorString, "2016-10-28 02:13:40")
    }

}