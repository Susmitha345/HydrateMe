package com.uk.ac.tees.mad.hydrateme.presentation.home

sealed interface HomeAction {
    data class AddWater(val amount: Int) : HomeAction
    // Future actions can be added here, e.g., navigating to other screens
}
