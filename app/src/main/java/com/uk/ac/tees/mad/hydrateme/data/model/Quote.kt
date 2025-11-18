package com.uk.ac.tees.mad.hydrateme.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Quote(
    val q: String, // The quote text
    val a: String, // The author
)
