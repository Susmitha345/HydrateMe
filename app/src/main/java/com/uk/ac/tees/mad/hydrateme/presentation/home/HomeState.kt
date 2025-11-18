package com.uk.ac.tees.mad.hydrateme.presentation.home

data class HomeState(
    val waterConsumed: Int = 0,
    val dailyGoal: Int = 2500, // Default daily goal
    val todayLogs: List<WaterLog> = emptyList(),
    val userName: String = "Alex",
    val quote: String = "The body is mainly water. The brain is 75% water, blood is 83% water, and muscles are 75% water. Drink up!",
    val quoteAuthor: String = "HydrateMe Team"
)
