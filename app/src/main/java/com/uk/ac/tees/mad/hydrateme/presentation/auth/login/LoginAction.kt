package com.uk.ac.tees.mad.hydrateme.presentation.auth.login

sealed interface LoginAction {
    data class OnEmailChange(val email: String) : LoginAction
    data class OnPasswordChange(val password: String) : LoginAction
    object OnLoginClick : LoginAction
    object OnForgotPasswordClick : LoginAction
    object OnCreateAccountClick : LoginAction
    object OnUnlockWithFingerprintClick : LoginAction
}
