package com.lanpet.core.testing.rule.data

import com.lanpet.core.common.loremIpsum
import com.lanpet.domain.model.free.FreeBoardCategoryType
import com.lanpet.domain.model.free.FreeBoardComment
import com.lanpet.domain.model.free.FreeBoardPostDetail
import com.lanpet.domain.model.free.FreeBoardResource
import com.lanpet.domain.model.PetCategory

val freeBoardCommentTestData =
    listOf(
        FreeBoardComment(
            id = 1,
            content = "댓글1",
            writer = "작성자1",
            writerImage = "https://dummyimage.com/600x400/000/fff",
            createdAt = "2021-08-01T12:34:56+09:00",
            updatedAt = "2021-08-01T12:34:56+09:00",
            freeBoardId = 1,
            likeCount = 10,
            commentCount = 5,
            subComments =
                listOf(
                    FreeBoardComment(
                        id = 1,
                        content = "대댓글1",
                        writer = "작성자1",
                        writerImage = "https://dummyimage.com/600x400/000/fff",
                        createdAt = "2021-08-01T12:34:56+09:00",
                        updatedAt = "2021-08-01T12:34:56+09:00",
                        freeBoardId = 1,
                        likeCount = 10,
                        commentCount = 5,
                    ),
                    FreeBoardComment(
                        id = 2,
                        content = "대댓글2",
                        writer = "작성자2",
                        writerImage = "https://dummyimage.com/600x400/000/fff",
                        createdAt = "2021-08-01T12:34:56+09:00",
                        updatedAt = "2021-08-01T12:34:56+09:00",
                        freeBoardId = 1,
                        likeCount = 10,
                        commentCount = 5,
                    ),
                ),
        ),
        FreeBoardComment(
            id = 2,
            content = "댓글2",
            writer = "작성자2",
            writerImage = "https://dummyimage.com/600x400/000/fff",
            createdAt = "2021-08-01T12:34:56+09:00",
            updatedAt = "2021-08-01T12:34:56+09:00",
            freeBoardId = 1,
            likeCount = 10,
            commentCount = 5,
        ),
    )

val freeBoardPostDetailTestData =
    FreeBoardPostDetail(
        id = "1",
        title = "제목",
        content = loremIpsum(),
        writer = "작성자",
        writerImage = "https://dummyimage.com/600x400/000/fff",
        createdAt = "2021-08-01T12:34:56+09:00",
        likeCount = 10,
        commentCount = 5,
        images =
        listOf(
            FreeBoardResource(
                id = "100",
                url = "https://dummyimage.com/600x400/000/fff",
            ),
        ),
        freeBoardCategory = FreeBoardCategoryType.CURIOUS,
        petCategory = PetCategory.DOG,
        isLike = true,
    )
