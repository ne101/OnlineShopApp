package com.example.onlineshopapp.data.di

import android.app.Application
import com.example.onlineshopapp.presentation.fragments.*
import dagger.BindsInstance
import dagger.Component


@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: CatalogFragment)
    fun inject(fragment: DetailInfoFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: FavouriteItemsFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}