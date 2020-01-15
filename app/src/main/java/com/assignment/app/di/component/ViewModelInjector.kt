package com.assignment.app.di.component

import com.assignment.app.data.source.network.ApiInterface
import com.assignment.app.di.module.NetworkModule
import com.assignment.app.viewmodel.DeliveryListViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified DeliveryListViewModel.
     */
    fun inject(deliveryListViewModel: DeliveryListViewModel)
    //fun inject(apiInterface: ApiInterface)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}
