package com.uk.ac.tees.mad.hydrateme.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.uk.ac.tees.mad.hydrateme.data.AuthRepositoryImpl
import com.uk.ac.tees.mad.hydrateme.presentation.auth.create_account.CreateAccountViewModel
import com.uk.ac.tees.mad.hydrateme.presentation.auth.forgot.ForgotViewModel
import com.uk.ac.tees.mad.hydrateme.presentation.auth.login.LoginViewModel
import com.uk.ac.tees.mad.hydrateme.domain.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {


    // Firebase
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }

    // Ktor Client
    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    // Supabase
    single {
        createSupabaseClient(
            supabaseUrl = "https://kvdagjrceetbstkqanlf.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imt2ZGFnanJjZWV0YnN0a3FhbmxmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjA1NTgwODQsImV4cCI6MjA3NjEzNDA4NH0.7S3oqGekCWggEMtaCne9JknjhqlGdpcxFZvUEurmRGI"
        ) {
            install(Storage)
        }
    }
    single { get<SupabaseClient>().storage }


    // Repositories
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    // Notification

    // ViewModels
    viewModel { CreateAccountViewModel(get()) }
    viewModel { ForgotViewModel(get()) }
    viewModel { LoginViewModel(get()) }

}
