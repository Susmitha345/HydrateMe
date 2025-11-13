package com.uk.ac.tees.mad.hydrateme.domain

import com.uk.ac.tees.mad.hydrateme.domain.util.DataError
import com.uk.ac.tees.mad.hydrateme.domain.util.EmptyResult
import com.uk.ac.tees.mad.hydrateme.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(email: String, password: String): EmptyResult<DataError.Firebase>
    suspend fun signUp(email: String, password: String, name: String): EmptyResult<DataError.Firebase>
    suspend fun forgotPassword(email: String): EmptyResult<DataError.Firebase>
    suspend fun logOut(): EmptyResult<DataError.Firebase>
}
