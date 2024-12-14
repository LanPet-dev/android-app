package com.lanpet.core.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.annotations.TestOnly

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

object PrimaryColor {
    val PRIMARY = Color(0xff6B5CFF)
}

object VioletColor {
    val Violet50 = Color(0xffEDEFFF)
    val Violet100 = Color(0xffDDE1FF)
    val Violet200 = Color(0xffC2C8FF)
    val Violet300 = Color(0xff9CA3FE)
    val Violet400 = Color(0xff7975FF)
    val Violet500 = Color(0xff6B5CFF)
    val Violet600 = Color(0xff5736F5)
    val Violet700 = Color(0xff3E25AE)
    val Violet800 = Color(0xff3E25AE)
    val Violet900 = Color(0xff342689)
}

object SubPrimaryColor {
    val Sub = Color(0xffFF5858)
}

object BlackColor {
    val Black = Color(0xff1c1d23)
    val HARD = Color(0xff000000)
    val MEDIUM = Color(0xff101010)
}

object GrayColor {
    val LIGHT = Color(0xffDBDBDB)
    val LIGHT_MEDIUM = Color(0xffBFBFBF)
    val MEDIUM = Color(0xff555555)
    val Gray950 = Color(0xff25262B)
    val Gray900 = Color(0xff383A43)
    val Gray800 = Color(0xff3F434D)
    val Gray700 = Color(0xff4B505D)
    val Gray600 = Color(0xff596070)
    val Gray500 = Color(0xff6F7686)
    val Gray400 = Color(0xff949AA8)
    val Gray300 = Color(0xffB5B9C4)
    val Gray200 = Color(0xffE3E4E8)
    val Gray100 = Color(0xffF1F1F4)
    val Gray50 = Color(0xffF7F8F8)
}

object PurpleColor {
    val MEDIUM = Color(0xff6608E1)
}

object WhiteColor {
    val LIGHT = Color(0xffffffff)
    val MEDIUM = Color(0xffdfdfdf)
    val White = Color(0xFFFFFFFF)
}

@Preview(showBackground = true, heightDp = 1500, widthDp = 800)
@Composable
private fun ColorSystemPreview() {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        Row {
            Column {
                Text("Primary Colors", style = MaterialTheme.typography.titleSmall)
                ColorRow("PRIMARY", PrimaryColor.PRIMARY)

                Spacer(modifier = Modifier.height(16.dp))
                Text("Sub Primary Colors", style = MaterialTheme.typography.titleSmall)

                Spacer(modifier = Modifier.height(16.dp))
                Text("Black Colors", style = MaterialTheme.typography.titleSmall)
                ColorRow("Black", BlackColor.Black)
                ColorRow("HARD", BlackColor.HARD)
                ColorRow("MEDIUM", BlackColor.MEDIUM)

                Spacer(modifier = Modifier.height(16.dp))
                Text("Gray Colors", style = MaterialTheme.typography.titleSmall)
                ColorRow("Gray900", GrayColor.Gray900)
                ColorRow("Gray800", GrayColor.Gray800)
                ColorRow("Gray700", GrayColor.Gray700)
                ColorRow("Gray600", GrayColor.Gray600)
                ColorRow("Gray500", GrayColor.Gray500)
                ColorRow("Gray400", GrayColor.Gray400)
                ColorRow("Gray300", GrayColor.Gray300)
                ColorRow("Gray200", GrayColor.Gray200)
                ColorRow("Gray100", GrayColor.Gray100)
                ColorRow("Gray50", GrayColor.Gray50)
                ColorRow("LIGHT", GrayColor.LIGHT)
                ColorRow("LIGHT_MEDIUM", GrayColor.LIGHT_MEDIUM)
                ColorRow("MEDIUM", GrayColor.MEDIUM)

                Spacer(modifier = Modifier.height(16.dp))
                Text("Purple Colors", style = MaterialTheme.typography.titleSmall)
                ColorRow("MEDIUM", PurpleColor.MEDIUM)
            }
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                Text("White Colors", style = MaterialTheme.typography.titleSmall)
                ColorRow("White", WhiteColor.White)
                ColorRow("LIGHT", WhiteColor.LIGHT)
                ColorRow("MEDIUM", WhiteColor.MEDIUM)

                Spacer(modifier = Modifier.height(16.dp))
                Text("Violet Colors", style = MaterialTheme.typography.titleSmall)
                ColorRow("Violet50", VioletColor.Violet50)
                ColorRow("Violet100", VioletColor.Violet100)
                ColorRow("Violet200", VioletColor.Violet200)
                ColorRow("Violet300", VioletColor.Violet300)
                ColorRow("Violet400", VioletColor.Violet400)
                ColorRow("Violet500", VioletColor.Violet500)
                ColorRow("Violet600", VioletColor.Violet600)
                ColorRow("Violet700", VioletColor.Violet700)
                ColorRow("Violet800", VioletColor.Violet800)
                ColorRow("Violet900", VioletColor.Violet900)

                Spacer(modifier = Modifier.height(16.dp))
                Text("Sub Primary Colors", style = MaterialTheme.typography.titleSmall)
                ColorRow("SubPrimary", SubPrimaryColor.Sub)
            }
        }
    }
}

@TestOnly
@Composable
private fun ColorRow(
    name: String,
    color: Color,
) {
    Row(
        modifier =
            Modifier
                .width(400.dp)
                .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .size(40.dp)
                    .background(color = color)
                    .border(1.dp, Color.Gray),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(name)
    }
}
