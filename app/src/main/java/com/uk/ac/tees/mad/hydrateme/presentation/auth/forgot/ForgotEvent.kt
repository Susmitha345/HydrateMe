package com.uk.ac.tees.mad.hydrateme.presentation.auth.forgot

import com.uk.ac.tees.mad.hydrateme.domain.util.DataError

sealed interface ForgotEvent {
    object Success : ForgotEvent
    data class Failure(val error: DataError.Firebase) : ForgotEvent
    object BackToLogin : ForgotEvent
}
