package com.uk.ac.tees.mad.hydrateme.presentation.auth.forgot

sealed interface ForgotAction {
    data class OnEmailChange(val email: String) : ForgotAction
    object OnSubmitClick : ForgotAction
    object OnBackToLoginClick : ForgotAction
}
