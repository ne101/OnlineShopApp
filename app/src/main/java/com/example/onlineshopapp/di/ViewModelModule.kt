package com.example.onlineshopapp.di

import androidx.lifecycle.ViewModel
import com.example.onlineshopapp.presentation.viewModels.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    fun bindRegistrationViewModel(viewModel: RegistrationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CatalogViewModel::class)
    fun bindRCatalogViewModel(viewModel: CatalogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailInfoViewModel::class)
    fun bindRDetailInfoViewModel(viewModel: DetailInfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindRProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteItemsViewModel::class)
    fun bindFavouriteItemsViewModel(viewModel: FavouriteItemsViewModel): ViewModel
}