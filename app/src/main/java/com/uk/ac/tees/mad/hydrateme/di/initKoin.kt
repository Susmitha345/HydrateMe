package com.uk.ac.tees.mad.hydrateme.di

import com.uk.ac.tees.mad.hydrateme.di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config : KoinAppDeclaration?=null){

    startKoin {
        config?.invoke(this)
        modules(

            appModule,
        )

    }
}