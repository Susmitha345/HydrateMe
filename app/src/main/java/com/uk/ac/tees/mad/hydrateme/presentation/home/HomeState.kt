package com.uk.ac.tees.mad.hydrateme.presentation.home

data class WaterLog(
    val time: String,
    val amount: Int
)

data class HomeState(
    val waterConsumed: Int = 0,
    val dailyGoal: Int = 2500, // Default daily goal
    val todayLogs: List<WaterLog> = emptyList(),
    val userName: String = "Alex"
)
