package com.lanpet.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.lanpet.core.designsystem.R


object AppTypography {
    val Pretendard = FontFamily(
        Font(R.font.pretendard_thin, FontWeight.Thin),
        Font(R.font.pretendard_light, FontWeight.Light),
        Font(R.font.pretendard_regular, FontWeight.Normal),
        Font(R.font.pretendard_medium, FontWeight.Medium),
        Font(R.font.pretendard_semibold, FontWeight.SemiBold),
        Font(R.font.pretendard_bold, FontWeight.Bold),
        Font(R.font.pretendard_extrabold, FontWeight.ExtraBold),
        Font(R.font.pretendard_black, FontWeight.Black)
    )

    // 공통 letter spacing (-2%)
    private const val COMMON_LETTER_SPACING = -0.02f

    // Title1 스타일
    val title1BoldSingle = TextStyle(
        fontFamily = Pretendard,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (24 * 1.16).sp  // 116%
    )

    val title1SemiBoldSingle = TextStyle(
        fontFamily = Pretendard,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (24 * 1.16).sp  // 116%
    )

    val title1BoldMulti = TextStyle(
        fontFamily = Pretendard,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (24 * 1.34).sp  // 134%
    )

    val title1SemiBoldMulti = TextStyle(
        fontFamily = Pretendard,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (24 * 1.34).sp  // 134%
    )

    // Title2 스타일
    val title2BoldSingle = TextStyle(
        fontFamily = Pretendard,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (20 * 1.18).sp  // 118%
    )

    val title2SemiBoldSingle = TextStyle(
        fontFamily = Pretendard,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (20 * 1.18).sp  // 118%
    )

    val title2BoldMulti = TextStyle(
        fontFamily = Pretendard,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (20 * 1.38).sp  // 138%
    )

    val title2SemiBoldMulti = TextStyle(
        fontFamily = Pretendard,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (20 * 1.38).sp  // 138%
    )

    // Title3 스타일
    val title3BoldSingle = TextStyle(
        fontFamily = Pretendard,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (18 * 1.20).sp  // 120%
    )

    val title3SemiBoldSingle = TextStyle(
        fontFamily = Pretendard,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (18 * 1.20).sp  // 120%
    )

    val title3BoldMulti = TextStyle(
        fontFamily = Pretendard,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (18 * 1.52).sp  // 152%
    )

    val title3SemiBoldMulti = TextStyle(
        fontFamily = Pretendard,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (18 * 1.52).sp  // 152%
    )

    val title3MediumMulti = TextStyle(
        fontFamily = Pretendard,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (18 * 1.52).sp  // 152%
    )

    // Body1 스타일
    val body1SemiBoldSingle = TextStyle(
        fontFamily = Pretendard,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (16 * 1.22).sp  // 122%
    )

    val body1RegularSingle = TextStyle(
        fontFamily = Pretendard,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (16 * 1.22).sp  // 122%
    )

    val body1SemiBoldMulti = TextStyle(
        fontFamily = Pretendard,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (16 * 1.60).sp  // 160%
    )

    val body1RegularMulti = TextStyle(
        fontFamily = Pretendard,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (16 * 1.60).sp  // 160%
    )

    // Body2 스타일
    val body2SemiBoldSingle = TextStyle(
        fontFamily = Pretendard,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (14 * 1.25).sp  // 125%
    )

    val body2MediumSingle = TextStyle(
        fontFamily = Pretendard,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (14 * 1.25).sp  // 125%
    )

    val body2SemiBoldMulti = TextStyle(
        fontFamily = Pretendard,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (14 * 1.72).sp  // 172%
    )

    val body2MediumMulti = TextStyle(
        fontFamily = Pretendard,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (14 * 1.72).sp  // 172%
    )

    // Body3 스타일
    val body3SemiBoldSingle = TextStyle(
        fontFamily = Pretendard,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (12 * 1.20).sp  // 120%
    )

    val body3MediumSingle = TextStyle(
        fontFamily = Pretendard,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (12 * 1.20).sp  // 120%
    )

    val body3SemiBoldMulti = TextStyle(
        fontFamily = Pretendard,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (12 * 1.65).sp  // 165%
    )

    val body3MediumMulti = TextStyle(
        fontFamily = Pretendard,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (12 * 1.65).sp  // 165%
    )

    // Sub1 스타일
    val sub1SemiBoldSingle = TextStyle(
        fontFamily = Pretendard,
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (10 * 1.28).sp  // 128%
    )

    val sub1MediumSingle = TextStyle(
        fontFamily = Pretendard,
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = COMMON_LETTER_SPACING.em,
        lineHeight = (10 * 1.20).sp  // 120%
    )
}

fun Typography.landingHead(): TextStyle {
    return this.headlineLarge
}

fun Typography.landingLabel(): TextStyle {
    return this.labelLarge.copy(Color.Gray)
}

val Typography = Typography(
    // Display
    displayLarge = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-2f).sp
    ),
    displayMedium = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = (-2f).sp
    ),
    displaySmall = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = (-2f).sp
    ),

    // Headline
    headlineLarge = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-2f).sp
    ),
    headlineMedium = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = (-2f).sp
    ),
    headlineSmall = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = (-2f).sp
    ),

    // Title
    titleLarge = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = (-2f).sp
    ),
    titleMedium = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (-2f).sp
    ),
    titleSmall = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (-2f).sp
    ),

    // Body
    bodyLarge = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (-2f).sp
    ),
    bodyMedium = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (-2f).sp
    ),
    bodySmall = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = (-2f).sp
    ),

    // Label
    labelLarge = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (-2f).sp
    ),
    labelMedium = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = (-2f).sp
    ),
    labelSmall = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = (-2f).sp
    )
)