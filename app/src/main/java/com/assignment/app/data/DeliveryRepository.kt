package com.assignment.app.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.assignment.app.data.model.Delivery
import com.assignment.app.data.source.local.AppPreferencesHelper
import com.assignment.app.data.source.local.DeliveryBoundaryCallback
import com.assignment.app.data.source.local.DeliveryDao
import com.assignment.app.data.source.network.ApiInterface
import com.assignment.app.utils.LIMIT
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class DeliveryRepository @Inject constructor(
    private val deliveryDao: DeliveryDao,
    private val apiInterface: ApiInterface,
    private var appPreferencesHelper: AppPreferencesHelper

) {
    lateinit var deliveryBoundaryCallback: DeliveryBoundaryCallback

    fun getPagedData(): LiveData<PagedList<Delivery>> {
        val config = PagedList.Config.Builder()
            .setPageSize(LIMIT)
            .setEnablePlaceholders(false)
            .build()
        val livePageListBuilder = LivePagedListBuilder<Int, Delivery>(
            deliveryDao.allDeliveries(), config
        )
        deliveryBoundaryCallback = DeliveryBoundaryCallback(apiInterface, deliveryDao,appPreferencesHelper)
        livePageListBuilder.setBoundaryCallback(deliveryBoundaryCallback)
        return livePageListBuilder.build()
    }


    fun setFav(delivery: Delivery?): Completable {
        return Completable.fromAction { deliveryDao.updateDelivery(delivery) }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }


    fun getDeliveries(apiInterface: ApiInterface): Observable<List<Delivery>> {
        return Observable.fromCallable { deliveryDao.all }
            .concatMap { dbDeliveryList ->
                if (dbDeliveryList.isEmpty()) {
                    apiInterface.getDeliveryList(0, LIMIT).concatMap { apiDeliveryList ->
                        deliveryDao.insertAll(apiDeliveryList)
                        Observable.just(apiDeliveryList)
                    }
                } else
                    Observable.just(dbDeliveryList)

            }

    }

    fun refreshData(apiInterface: ApiInterface): Observable<List<Delivery>> {
        return apiInterface.getDeliveryList(0, LIMIT)
            .doOnNext {
                deliveryDao.deleteAll()
                deliveryDao.insertAll(it)
            }
            .doOnError {

            }
    }


}