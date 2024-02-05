package com.example.onlineshopapp.di

import android.app.Application
import com.example.data.data.api.ApiFactory
import com.example.data.data.api.ApiService
import com.example.data.data.database.AppDataBase
import com.example.data.data.database.ItemListDao
import com.example.data.data.repositoryImpl.RepositoryImpl
import com.example.domain.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindRepository(impl: com.example.data.data.repositoryImpl.RepositoryImpl): com.example.domain.domain.repository.Repository

    companion object {

        @Provides
        fun provideApiService(): com.example.data.data.api.ApiService = com.example.data.data.api.ApiFactory.apiService

        @Provides
        fun provideItemListDao(application: Application): com.example.data.data.database.ItemListDao {
            return com.example.data.data.database.AppDataBase.getInstance(application).itemListDao()
        }

    }
}