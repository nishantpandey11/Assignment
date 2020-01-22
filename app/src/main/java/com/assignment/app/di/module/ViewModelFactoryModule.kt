package com.assignment.app.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.app.di.ViewModelKey
import com.assignment.app.viewmodel.DeliveryListViewModel
import com.assignment.app.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DeliveryListViewModel::class)
    abstract fun bindListViewModel(viewModel: DeliveryListViewModel): ViewModel
}