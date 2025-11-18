package com.uk.ac.tees.mad.hydrateme.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uk.ac.tees.mad.hydrateme.data.repository.QuoteRepository
import com.uk.ac.tees.mad.hydrateme.domain.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SplashState(
    val quote: String = "",
    val author: String = "",
    val isAuthenticated: Boolean? = null
)

class SplashViewModel(
    private val authRepository: AuthRepository,
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val quote = quoteRepository.getRandomQuote()
            _state.value = _state.value.copy(
                quote = quote?.q ?: "Hydrate and feel great!",
                author = quote?.a ?: "HydrateMe Team"
            )
            _state.value = _state.value.copy(isAuthenticated = authRepository.isUserAuthenticated())
        }
    }
}
