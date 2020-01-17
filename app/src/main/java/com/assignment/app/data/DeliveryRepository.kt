package com.assignment.app.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.assignment.app.data.model.Delivery
import com.assignment.app.data.source.local.DeliveryBoundaryCallback
import com.assignment.app.data.source.local.DeliveryDao
import com.assignment.app.data.source.network.ApiInterface
import com.assignment.app.utils.LIMIT
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class DeliveryRepository(private val deliveryDao: DeliveryDao) {

    fun getPagedData(apiInterface: ApiInterface): LiveData<PagedList<Delivery>> {
        val config = PagedList.Config.Builder()
            .setPageSize(LIMIT)
            .setEnablePlaceholders(false)
            .build()
        val livePageListBuilder = LivePagedListBuilder<Int, Delivery>(
            deliveryDao.allDeliveries(), config
        )
        livePageListBuilder.setBoundaryCallback(DeliveryBoundaryCallback(apiInterface, deliveryDao))
        return livePageListBuilder.build()
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

    fun setFav(delivery: Delivery) {
        Completable.fromAction { deliveryDao.updateDelivery(delivery) }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {

                }

                override fun onError(e: Throwable) {
                }
            })


    }


}