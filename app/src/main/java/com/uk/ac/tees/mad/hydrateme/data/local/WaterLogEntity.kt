package com.uk.ac.tees.mad.hydrateme.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "water_logs")
data class WaterLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Int = 0,
    val timestamp: Date = Date()
)
