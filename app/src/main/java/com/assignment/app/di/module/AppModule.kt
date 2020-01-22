package com.assignment.app.di.module

import android.app.Application
import androidx.room.Room
import com.assignment.app.DeliveryApp
import com.assignment.app.data.DeliveryRepository
import com.assignment.app.data.source.local.AppDatabase
import com.assignment.app.data.source.local.DeliveryBoundaryCallback
import com.assignment.app.data.source.local.DeliveryDao
import com.assignment.app.data.source.network.ApiInterface
import com.assignment.app.utils.DB_NAME
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideApplication(): DeliveryApp = DeliveryApp()

    @Provides
    fun provideDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(application, AppDatabase::class.java, DB_NAME)
            .build()

    @Provides
    fun provideDeliveryDao(db: AppDatabase): DeliveryDao = db.deliveryDao()

    @Provides
    fun provideDeliveryRepository(
        deliveryDao: DeliveryDao,
        apiInterface: ApiInterface
    ): DeliveryRepository =
        DeliveryRepository(deliveryDao, apiInterface)

    @Provides
    fun provideDeliveryBoundaryCallback(
        apiInterface: ApiInterface,
        deliveryDao: DeliveryDao
    ): DeliveryBoundaryCallback = DeliveryBoundaryCallback(apiInterface, deliveryDao)


}