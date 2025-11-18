package com.uk.ac.tees.mad.hydrateme.presentation.home

sealed interface HomeAction {
    data class AddWater(val amount: Int) : HomeAction
    object FetchNewQuote : HomeAction
    object SyncLogs : HomeAction
}
