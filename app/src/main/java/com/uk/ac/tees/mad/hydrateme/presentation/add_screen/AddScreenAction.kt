package com.uk.ac.tees.mad.hydrateme.presentation.add_screen

import java.time.LocalTime

sealed interface AddScreenAction {
    data class OnAmountChange(val amount: String) : AddScreenAction
    data class OnUnitChange(val unit: WaterUnit) : AddScreenAction
    data class OnNoteChange(val note: String) : AddScreenAction
    data class OnTimeChange(val time: LocalTime) : AddScreenAction
    data object OnSaveClick : AddScreenAction
    data object OnCancelClick : AddScreenAction
    data object OnIncrementAmount : AddScreenAction
    data object OnDecrementAmount : AddScreenAction
}
