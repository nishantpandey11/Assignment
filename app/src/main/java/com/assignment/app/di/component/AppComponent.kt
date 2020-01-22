package com.assignment.app.di.component

import android.app.Application
import com.assignment.app.di.module.AppModule
import com.assignment.app.di.module.NetworkModule
import com.assignment.app.di.module.ViewModelFactoryModule
import com.assignment.app.view.ui.DeliveryDetailActivity
import com.assignment.app.view.ui.DeliveryListActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton



@Singleton
@Component(modules = [ViewModelFactoryModule::class,NetworkModule::class, AppModule::class])
interface AppComponent {
    fun inject(app: DeliveryDetailActivity)
    fun inject(app: DeliveryListActivity)


    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        @BindsInstance
        fun application(application: Application): Builder
    }

}
