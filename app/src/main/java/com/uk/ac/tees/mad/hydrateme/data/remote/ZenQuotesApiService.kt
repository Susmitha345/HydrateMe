package com.uk.ac.tees.mad.hydrateme.data.remote

import com.uk.ac.tees.mad.hydrateme.data.model.Quote
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface ZenQuotesApiService {
    suspend fun getRandomQuote(): List<Quote>

    companion object {
        fun create(): ZenQuotesApiService {
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    })
                }
            }
            return ZenQuotesApiServiceImpl(client)
        }
    }
}

class ZenQuotesApiServiceImpl(private val client: HttpClient) : ZenQuotesApiService {
    private val baseUrl = "https://zenquotes.io/api/random"

    override suspend fun getRandomQuote(): List<Quote> {
        return try {
            client.get(baseUrl).body()
        } catch (e: Exception) {
            // In a real app, handle exceptions gracefully (e.g., logging, default quote)
            emptyList()
        }
    }
}
