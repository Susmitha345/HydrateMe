package com.uk.ac.tees.mad.hydrateme.presentation.auth.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)