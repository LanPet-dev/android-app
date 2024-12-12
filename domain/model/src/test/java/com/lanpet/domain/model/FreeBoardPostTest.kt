package com.lanpet.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat

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
            id = 1,
            updatedAt = "2016-11-24T17:13:40+00:00",
        )

        assertEquals(post.createdAtKorString, "2016-10-28 02:13:40")
    }

    @Nested
    inner class CreatedAtPostStringTest {
        @Test
        fun `createdAtPostString, year diff test`() {
            val post = Post(
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

            assertEquals(
                post.createdAtPostString(
                    currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-10-27 17:13:40")!!
                ), "5년 전"
            )

            assertEquals(
                post.createdAtPostString(
                    currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-10-27 17:13:40")!!
                ), "6년 전"
            )
        }

        @Test
        fun `createdAtPostString, month diff test`() {
            val post = Post(
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

            assertEquals(
                post.createdAtPostString(
                    currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-11-27 17:13:40")!!
                ), "1달 전"
            )

            assertEquals(
                post.createdAtPostString(
                    currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-12-27 17:13:40")!!
                ), "2달 전"
            )
        }

        @Test
        fun `createdAtPostString, day diff test`() {
            val post = Post(
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

            assertEquals(
                post.createdAtPostString(
                    currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-10-29 17:13:40")!!
                ), "1일 전"
            )

            assertEquals(
                post.createdAtPostString(
                    currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-10-30 17:13:40")!!
                ), "2일 전"
            )
        }

        @Test
        fun `createdAtPostString, hour diff test`() {
            val post = Post(
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

            assertEquals(
                post.createdAtPostString(
                    currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-10-28 03:13:40")!!
                ), "1시간 전"
            )

            assertEquals(
                post.createdAtPostString(
                    currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-10-28 03:40:40")!!
                ), "1시간 전"
            )

            assertEquals(
                post.createdAtPostString(
                    currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-10-28 19:12:40")!!
                ), "16시간 전"
            )

            assertEquals(
                post.createdAtPostString(
                    currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-10-28 19:14:40")!!
                ), "17시간 전"
            )
        }

        @Test
        fun `createdAtPostString, minute diff test`() {
            val post = Post(
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

            assertEquals(
                post.createdAtPostString(
                    currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-10-28 02:14:40")!!
                ), "1분 전"
            )

            assertEquals(
                post.createdAtPostString(
                    currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-10-28 02:15:40")!!
                ), "2분 전"
            )
        }

        @Test
        fun `createdAtPostString, now test`() {
            val post = Post(
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

            assertEquals(
                post.createdAtPostString(
                    currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-10-28 02:13:50")!!
                ), "방금 전"
            )
        }
    }
}