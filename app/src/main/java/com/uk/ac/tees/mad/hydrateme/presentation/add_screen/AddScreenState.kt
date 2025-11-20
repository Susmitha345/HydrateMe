package com.uk.ac.tees.mad.hydrateme.presentation.add_screen

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime

data class AddScreenState @RequiresApi(Build.VERSION_CODES.O) constructor(
    val amount: String = "250",
    val unit: WaterUnit = WaterUnit.ML,
    val note: String = "",
    val time: LocalTime = LocalTime.now()
)

enum class WaterUnit(val text: String) {
    ML("ml"),
    OZ("oz")
}
