import com.lanpet.core.common.createdAtPostString
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ExtensionsTest {
    @Nested
    inner class CreatedAtFreeBoardPostStringTest {
        @Test
        fun `createdAtPostString, year diff test`() {
            _root_ide_package_.org.junit.jupiter.api.Assertions.assertEquals(
                createdAtPostString(
                    createdAt = "2016-10-27T17:13:40+00:00",
                    currentTime = "2021-10-27 8:13:40",
                ),
                "5년 전",
            )

            _root_ide_package_.org.junit.jupiter.api.Assertions.assertEquals(
                createdAtPostString(
                    createdAt = "2016-10-27T17:13:40+00:00",
                    currentTime = "2022-10-27 8:13:40",
                ),
                "6년 전",
            )
        }

        @Test
        fun `createdAtPostString, month diff test`() {
            _root_ide_package_.org.junit.jupiter.api.Assertions.assertEquals(
                createdAtPostString(
                    createdAt = "2016-10-27T17:13:40+00:00",
                    currentTime = "2016-11-27 21:13:40",
                ),
                "1달 전",
            )

            _root_ide_package_.org.junit.jupiter.api.Assertions.assertEquals(
                createdAtPostString(
                    createdAt = "2016-10-27T17:13:40+00:00",
                    currentTime = "2016-12-27 21:13:40",
                ),
                "2달 전",
            )
        }

        @Test
        fun `createdAtPostString, day diff test`() {
            _root_ide_package_.org.junit.jupiter.api.Assertions.assertEquals(
                createdAtPostString(
                    createdAt = "2016-10-27T17:13:40+00:00",
                    currentTime = "2016-10-29 02:13:40",
                ),
                "1일 전",
            )

            _root_ide_package_.org.junit.jupiter.api.Assertions.assertEquals(
                createdAtPostString(
                    createdAt = "2016-10-27T17:13:40+00:00",
                    currentTime = "2016-10-30 2:13:40",
                ),
                "2일 전",
            )
        }

        @Test
        fun `createdAtPostString, hour diff test`() {
            _root_ide_package_.org.junit.jupiter.api.Assertions.assertEquals(
                createdAtPostString(
                    createdAt = "2016-10-28T17:13:40+00:00",
                    currentTime = "2016-10-29 3:13:40",
                ),
                "1시간 전",
            )

            _root_ide_package_.org.junit.jupiter.api.Assertions.assertEquals(
                createdAtPostString(
                    createdAt = "2016-10-28T03:40:40+00:00",
                    currentTime = "2016-10-28 14:40:40",
                ),
                "2시간 전",
            )

            _root_ide_package_.org.junit.jupiter.api.Assertions.assertEquals(
                createdAtPostString(
                    createdAt = "2016-10-28T19:12:40+00:00",
                    currentTime = "2016-10-29 20:12:40",
                ),
                "16시간 전",
            )
        }

        @Test
        fun `createdAtPostString, minute diff test`() {
            _root_ide_package_.org.junit.jupiter.api.Assertions.assertEquals(
                createdAtPostString(
                    createdAt = "2016-10-28T04:13:40+00:00",
                    currentTime = "2016-10-28 13:14:40",
                ),
                "1분 전",
            )

            _root_ide_package_.org.junit.jupiter.api.Assertions.assertEquals(
                createdAtPostString(
                    createdAt = "2016-10-28T04:12:40+00:00",
                    currentTime = "2016-10-28 13:14:40",
                ),
                "2분 전",
            )
        }

        @Test
        fun `createdAtPostString, now test`() {
            _root_ide_package_.org.junit.jupiter.api.Assertions.assertEquals(
                createdAtPostString(
                    createdAt = "2021-10-28T04:13:40+00:00",
                    currentTime = "2021-10-28 13:13:40",
                ),
                "방금 전",
            )
        }
    }
}
