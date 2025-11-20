package com.uk.ac.tees.mad.hydrateme.presentation.add_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class AddScreenViewModel : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val _state = MutableStateFlow(AddScreenState())
    @RequiresApi(Build.VERSION_CODES.O)
    val state = _state.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = AddScreenState()
        )

    @RequiresApi(Build.VERSION_CODES.O)
    fun onAction(action: AddScreenAction) {
        when (action) {
            is AddScreenAction.OnAmountChange -> _state.update { it.copy(amount = action.amount) }
            is AddScreenAction.OnNoteChange -> _state.update { it.copy(note = action.note) }
            is AddScreenAction.OnTimeChange -> _state.update { it.copy(time = action.time) }
            is AddScreenAction.OnUnitChange -> _state.update { it.copy(unit = action.unit) }
            AddScreenAction.OnIncrementAmount -> {
                val currentAmount = _state.value.amount.toIntOrNull() ?: 0
                _state.update { it.copy(amount = (currentAmount + 1).toString()) }
            }
            AddScreenAction.OnDecrementAmount -> {
                val currentAmount = _state.value.amount.toIntOrNull() ?: 0
                if (currentAmount > 0) {
                    _state.update { it.copy(amount = (currentAmount - 1).toString()) }
                }
            }
            // TODO: Handle Save and Cancel actions
            AddScreenAction.OnCancelClick -> TODO()
            AddScreenAction.OnSaveClick -> TODO()
        }
    }
}
