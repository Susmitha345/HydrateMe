package com.uk.ac.tees.mad.hydrateme.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.uk.ac.tees.mad.hydrateme.data.AuthRepositoryImpl
import com.uk.ac.tees.mad.hydrateme.data.local.AppDatabase
import com.uk.ac.tees.mad.hydrateme.data.remote.ZenQuotesApiService
import com.uk.ac.tees.mad.hydrateme.data.repository.QuoteRepository
import com.uk.ac.tees.mad.hydrateme.data.repository.QuoteRepositoryImpl
import com.uk.ac.tees.mad.hydrateme.data.repository.WaterDataRepository
import com.uk.ac.tees.mad.hydrateme.data.repository.WaterDataRepositoryImpl
import com.uk.ac.tees.mad.hydrateme.domain.AuthRepository
import com.uk.ac.tees.mad.hydrateme.presentation.auth.login.LoginViewModel
import com.uk.ac.tees.mad.hydrateme.presentation.home.HomeViewModel
import com.uk.ac.tees.mad.hydrateme.presentation.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        AppDatabase.getDatabase(androidContext()).waterLogDao()
    }

    single<QuoteRepository> {
        QuoteRepositoryImpl(apiService = get())
    }

    single<WaterDataRepository> {
        val auth = get<FirebaseAuth>()
        val userId = auth.currentUser?.uid ?: "default_user" // Use a default or handle unauthenticated state
        WaterDataRepositoryImpl(waterLogDao = get(), firestore = get(), userId = userId)
    }

    single<AuthRepository> {
        AuthRepositoryImpl(firebaseAuth = get(), firestore = get())
    }

    single {
        ZenQuotesApiService.create()
    }

    single {
        FirebaseFirestore.getInstance()
    }

    single {
        FirebaseAuth.getInstance()
    }

    viewModel {
        HomeViewModel(waterDataRepository = get(), quoteRepository = get())
    }

    viewModel {
        LoginViewModel(authRepository = get())
    }

    viewModel {
        SplashViewModel(authRepository = get(), quoteRepository = get())
    }
}
