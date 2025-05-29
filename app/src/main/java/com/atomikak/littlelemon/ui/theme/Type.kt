package com.atomikak.littlelemon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.unit.sp
import com.atomikak.littlelemon.R
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.GoogleFont.Provider

val provider = Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val markaziFont = GoogleFont("Markazi Text")
val karlaFont = GoogleFont("Karla")

val MarkaziFamily = FontFamily(
    Font(googleFont = markaziFont, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = markaziFont, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = markaziFont, fontProvider = provider, weight = FontWeight.Bold),
    Font(googleFont = markaziFont, fontProvider = provider, weight = FontWeight.ExtraBold),
)

val KarlaFamily = FontFamily(
    Font(googleFont = karlaFont, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = karlaFont, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = karlaFont, fontProvider = provider, weight = FontWeight.Bold),
    Font(googleFont = karlaFont, fontProvider = provider, weight = FontWeight.ExtraBold),
)

val Typography = Typography(
    displayLarge = TextStyle( // Display Title - Medium 64pt
        fontFamily = MarkaziFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 64.sp
    ),
    displayMedium = TextStyle( // Subtitle - close to display
        fontFamily = MarkaziFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp
    ),
    titleLarge = TextStyle( // Section Title! Uppercase - 20pt Extra Bold
        fontFamily = KarlaFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle( // This Week's Specials - 16pt Extra Bold
        fontFamily = KarlaFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp
    ),
    titleSmall = TextStyle( // Card Title - 18pt Bold
        fontFamily = KarlaFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    bodyLarge = TextStyle( // Lead Text - 18pt Medium
        fontFamily = KarlaFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    bodyMedium = TextStyle( // Paragraph - 16pt Regular
        fontFamily = KarlaFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodySmall = TextStyle( // Highlight Text - 16pt Medium
        fontFamily = KarlaFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )
)

//val Typography = Typography(
//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    )
//    /* Other default text styles to override
//    titleLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 22.sp,
//        lineHeight = 28.sp,
//        letterSpacing = 0.sp
//    ),
//    labelSmall = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Medium,
//        fontSize = 11.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp
//    )
//    */
//)
//
//val karlaFontFamily = FontFamily(
//    Font(R.font.karla_regular, FontWeight.Normal)
//)
//
//val markaziFontFamily = FontFamily(
//    Font(R.font.markazi_text_regular, FontWeight.Normal)
//)