package com.example.onlineshopapp.data.di

import android.app.Application
import com.example.onlineshopapp.data.api.ApiFactory
import com.example.onlineshopapp.data.api.ApiService
import com.example.onlineshopapp.data.database.AppDataBase
import com.example.onlineshopapp.data.database.ItemListDao
import com.example.onlineshopapp.data.repositoryImpl.RepositoryImpl
import com.example.onlineshopapp.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

    companion object {

        @Provides
        fun provideApiService(): ApiService = ApiFactory.apiService

        @Provides
        fun provideItemListDao(application: Application): ItemListDao {
            return AppDataBase.getInstance(application).itemListDao()
        }

    }
}