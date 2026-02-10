package com.somila.data.di

import com.somila.data.di.repository.PostRepositoryImpl
import com.somila.domain.repository.PostRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    singleOf(::PostRepositoryImpl) bind PostRepository::class
}