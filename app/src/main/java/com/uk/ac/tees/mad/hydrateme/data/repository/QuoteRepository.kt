package com.uk.ac.tees.mad.hydrateme.data.repository

import com.uk.ac.tees.mad.hydrateme.data.model.Quote
import com.uk.ac.tees.mad.hydrateme.data.remote.ZenQuotesApiService

interface QuoteRepository {
    suspend fun getRandomQuote(): Quote?
}

class QuoteRepositoryImpl(private val apiService: ZenQuotesApiService) : QuoteRepository {
    override suspend fun getRandomQuote(): Quote? {
        return try {
            apiService.getRandomQuote().firstOrNull()
        } catch (e: Exception) {
            // Handle exceptions (e.g., network issues)
            null
        }
    }
}
