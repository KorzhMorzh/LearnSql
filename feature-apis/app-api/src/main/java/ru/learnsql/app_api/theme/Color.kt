package ru.learnsql.app_api.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Orange = Color(0xFFFF6B00)
val Yellow = Color(0xFFFFC727)
val Red = Color(0xFFFF3D00)
val Blue = Color(0xFF1D51A3)
val DisabledBlue = Color(0xFF003161)
val LightBlue = Color(0xFF1976D2)
val DisabledLightBlue = Color(0xFF1463B0)
val DarkBlue = Color(0xFF0C3577)
val Green = Color(0xFF00CF8A)
val Gray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF535353)
val NonActiveGray = Color(0xFFC4C4C4)
val LightGray = Color(0xFFF9F9F9)
val WhiteGray = Color(0xFFE5E5E5)
private val BottomGradient = Color(0xFF1D51A3)
private val TopGradient = Color(0xFF082A60)

val BlueGradient = Brush.verticalGradient(
    colors = listOf(
        BottomGradient,
        TopGradient
    )
)