package com.uk.ac.tees.mad.hydrateme.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here (e.g., from a database or repository) **/
                // For demonstration, we'll populate it with some sample data.
                _state.value = HomeState(
                    waterConsumed = 1200,
                    dailyGoal = 2500,
                    todayLogs = listOf(
                        WaterLog("8:30 AM", 300),
                        WaterLog("10:15 AM", 250),
                        WaterLog("1:00 PM", 400),
                        WaterLog("3:45 PM", 250)
                    ),
                    userName = "Alex"
                )
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HomeState()
        )

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.AddWater -> addWater(action.amount)
        }
    }

    private fun addWater(amount: Int) {
        val currentTime = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())
        val newLog = WaterLog(time = currentTime, amount = amount)

        _state.update { currentState ->
            currentState.copy(
                waterConsumed = currentState.waterConsumed + amount,
                todayLogs = currentState.todayLogs + newLog
            )
        }
    }
}
