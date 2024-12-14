import com.lanpet.core.common.createdAtPostString
import com.lanpet.core.common.toLocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone

class ExtensionsTest {
    private val timeZone: TimeZone = TimeZone.getDefault()
    private val createdAt = "2016-10-27T17:13:40+00:00"
    private val currentTime = createdAt.toLocalDate()
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat(" yyyy-MM-dd HH:mm:ss")

    @Nested
    inner class `CreatedAtPostString Test` {
        @Test
        fun `createdAtPostString, year diff test`() {
            calendar.setTime(currentTime)
            calendar.add(Calendar.YEAR, 5)

            assertEquals(
                createdAtPostString(
                    createdAt = createdAt,
                    currentTime = dateFormat.format(calendar.time),
                ),
                "5년 전",
            )

            calendar.setTime(currentTime)
            calendar.add(Calendar.YEAR, 6)

            assertEquals(
                createdAtPostString(
                    createdAt = createdAt,
                    currentTime = dateFormat.format(calendar.time),
                ),
                "6년 전",
            )
        }

        @Test
        fun `createdAtPostString, month diff test`() {
            calendar.setTime(currentTime)
            calendar.add(Calendar.MONTH, 1)

            assertEquals(
                createdAtPostString(
                    createdAt = createdAt,
                    currentTime = dateFormat.format(calendar.time),
                ),
                "1달 전",
            )

            calendar.setTime(currentTime)
            calendar.add(Calendar.MONTH, 2)

            assertEquals(
                createdAtPostString(
                    createdAt = createdAt,
                    currentTime = dateFormat.format(calendar.time),
                ),
                "2달 전",
            )
        }

        @Test
        fun `createdAtPostString, day diff test`() {
            calendar.setTime(currentTime)
            calendar.add(Calendar.DATE, 1)

            assertEquals(
                createdAtPostString(
                    createdAt = createdAt,
                    currentTime = dateFormat.format(calendar.time),
                ),
                "1일 전",
            )

            calendar.setTime(currentTime)
            calendar.add(Calendar.DATE, 2)

            assertEquals(
                createdAtPostString(
                    createdAt = createdAt,
                    currentTime = dateFormat.format(calendar.time),
                ),
                "2일 전",
            )
        }

        @Test
        fun `createdAtPostString, hour diff test`() {
            calendar.setTime(currentTime)
            calendar.add(Calendar.HOUR, 1)

            assertEquals(
                createdAtPostString(
                    createdAt = createdAt,
                    currentTime = dateFormat.format(calendar.time),
                ),
                "1시간 전",
            )

            calendar.setTime(currentTime)
            calendar.add(Calendar.HOUR, 2)

            assertEquals(
                createdAtPostString(
                    createdAt = createdAt,
                    currentTime = dateFormat.format(calendar.time),
                ),
                "2시간 전",
            )

            calendar.setTime(currentTime)
            calendar.add(Calendar.HOUR, 16)

            assertEquals(
                createdAtPostString(
                    createdAt = createdAt,
                    currentTime = dateFormat.format(calendar.time),
                ),
                "16시간 전",
            )
        }

        @Test
        fun `createdAtPostString, minute diff test`() {
            calendar.setTime(currentTime)
            calendar.add(Calendar.MINUTE, 1)

            assertEquals(
                createdAtPostString(
                    createdAt = createdAt,
                    currentTime = dateFormat.format(calendar.time),
                ),
                "1분 전",
            )

            calendar.setTime(currentTime)
            calendar.add(Calendar.MINUTE, 2)

            assertEquals(
                createdAtPostString(
                    createdAt = createdAt,
                    currentTime = dateFormat.format(calendar.time),
                ),
                "2분 전",
            )
        }

        @Test
        fun `createdAtPostString, now test`() {
            calendar.setTime(currentTime)
            calendar.add(Calendar.SECOND, 10)

            assertEquals(
                createdAtPostString(
                    createdAt = createdAt,
                    currentTime = dateFormat.format(calendar.time),
                ),
                "방금 전",
            )

            calendar.setTime(currentTime)
            calendar.add(Calendar.SECOND, 59)

            assertEquals(
                createdAtPostString(
                    createdAt = createdAt,
                    currentTime = dateFormat.format(calendar.time),
                ),
                "방금 전",
            )
        }
    }
}
