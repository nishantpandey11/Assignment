package com.assignment.app.data

import com.assignment.app.data.model.Delivery
import com.assignment.app.data.source.local.DeliveryDao
import com.assignment.app.data.source.network.ApiInterface
import com.assignment.app.utils.LIMIT
import io.reactivex.Observable
import javax.inject.Inject

class DeliveryRepository(private val deliveryDao: DeliveryDao) {
    @Inject
    lateinit var apiInterface: ApiInterface



    fun getDeliveries(apiInterface: ApiInterface): Observable<List<Delivery>> {
        return apiInterface.getDeliveryList(0, LIMIT).concatMap { dbDeliveryList ->
            if (dbDeliveryList.isEmpty()) {
                apiInterface.getDeliveryList(0, LIMIT).concatMap { apiPostList ->
                    deliveryDao.insertAll(*apiPostList.toTypedArray())
                    Observable.just(apiPostList)
                }
            } else
                Observable.just(dbDeliveryList)
        }
      /*  return apiInterface.getDeliveryList(0, LIMIT)
            .doOnNext {
                Log.e("REPOSITORY API * ", it.size.toString())
                //for (item in it) {
                    deliveryDao.insertAll(it)
                //}
            }*/
    }
   /* fun getDeliveriesFromDb(limit: Int, offset: Int): Observable<List<Delivery>> {
        return deliveryDao.getDeliveries(limit, offset)
            .toObservable()
            .doOnNext {
                Log.e("REPOSITORY DB *** ", it.size.toString())
            }
    }*/
}