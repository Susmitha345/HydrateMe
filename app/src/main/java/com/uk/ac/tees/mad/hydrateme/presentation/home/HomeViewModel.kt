package com.uk.ac.tees.mad.hydrateme.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uk.ac.tees.mad.hydrateme.data.repository.QuoteRepository
import com.uk.ac.tees.mad.hydrateme.data.repository.WaterDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val waterDataRepository: WaterDataRepository,
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        fetchNewQuote()
        observeWaterLogs()
        syncLogs()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.AddWater -> addWater(action.amount)
            HomeAction.FetchNewQuote -> fetchNewQuote()
            HomeAction.SyncLogs -> syncLogs()
        }
    }

    private fun addWater(amount: Int) {
        viewModelScope.launch {
            waterDataRepository.addWaterLog(amount)
        }
    }

    private fun fetchNewQuote() {
        viewModelScope.launch {
            val quote = quoteRepository.getRandomQuote()
            if (quote != null) {
                _state.update { it.copy(quote = quote.q, quoteAuthor = quote.a) }
            }
        }
    }

    private fun observeWaterLogs() {
        viewModelScope.launch {
            waterDataRepository.getTodayLogs().collectLatest { logs ->
                val totalConsumed = logs.sumOf { it.amount }
                _state.update {
                    it.copy(
                        todayLogs = logs,
                        waterConsumed = totalConsumed
                    )
                }
            }
        }
    }

    private fun syncLogs() {
        viewModelScope.launch {
            waterDataRepository.syncLogs()
        }
    }
}
