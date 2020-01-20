package com.assignment.app.data.source.local

import android.util.Log
import androidx.paging.PagedList
import com.assignment.app.data.model.Delivery
import com.assignment.app.data.source.network.ApiInterface
import com.assignment.app.utils.LIMIT
import com.assignment.app.utils.NetworkUtility
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException

class DeliveryBoundaryCallback(
    private val apiInterface: ApiInterface,
    private val deliveryDao: DeliveryDao
) : PagedList.BoundaryCallback<Delivery>() {
    var totalCount: Int = 0
    private var isLoaded: Boolean = false
    var disposable = CompositeDisposable()

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        fetchDeliveries(0, LIMIT)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Delivery) {
        super.onItemAtEndLoaded(itemAtEnd)
        Log.e("onItemAtEndLoaded", "--")
        if (!isLoaded) {
            Log.e("onItemAtEndLoaded", "$isLoaded")
            fetchDeliveries(totalCount, LIMIT)
        }
    }

    fun fetchDeliveries(offset: Int, pageSize: Int) {
        Log.e("PK", "offset: $offset, pageSize: $pageSize")
        // todo need to add network check
        /*if (!NetworkUtility.isInternetAvailable()) {
            return
        }*/

        if (offset == 0) {
            Log.e("fetchDeiveries", "loading for the first time")
        } else {
            Log.e("fetchDeiveries", "loading")
        }

        apiInterface.getDeliveryList(offset, pageSize).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> success(result) }, { error -> error(error) })

        /* disposable.add(
             getDeliveryListTask
                 .buildUseCase(
                     GetDeliveryListTask.Params(
                         startIndex = offset.toString(),
                         limit = pageSize
                     )
                 )
                 .map { Resource.success(it) }
                 .startWith(Resource.loading())
                 .onErrorResumeNext(
                     Function {
                         io.reactivex.Observable.just(Resource.error(it))
                     }
                 ).toFlowable(BackpressureStrategy.LATEST)
                 .subscribe({ result ->
                     when (result.status) {
                         Status.SUCCESS -> {
                             success(result.data)
                         }
                         Status.ERROR -> {
                             error(result.throwable)
                         }
                     }
                 }, { e -> error(e) })
         )*/
    }

    private fun success(list: List<Delivery>?) {
        if (list == null) {
            isLoaded = true
            Log.e("fetchDeliveries success", "nothing to show")
        } else {
            totalCount += list.size
            if (list.size < LIMIT) {
                isLoaded = true
                Log.e("fetchDeliveries success", "eof")
            } else {
                /*Completable.complete().subscribeOn(Schedulers.io())
                    .subscribe{
                        deliveryDao.insertAll(list)
                    }*/
                Log.e("fetchDeliveries success", "list inserted in db" + list.size)
            }
            Completable.complete().subscribeOn(Schedulers.io())
                .subscribe {
                    deliveryDao.insertAll(list)
                }
        }
    }

    private fun error(throwable: Throwable?) {
        throwable?.let {
            throwable.printStackTrace()
            Log.e("fetchDeliveries error", throwable.message.toString())
            if (throwable is UnknownHostException) {
                Log.e("fetchDeliveries error", "Network Error")
                retry()
            } else {
                Log.e("fetchDeliveries error", " Something went wrong")
            }
        }
    }

    /* fun updateState(state: Status) {
         this.status.postValue(state)
     }*/

    fun retry() {
        fetchDeliveries(totalCount, LIMIT)
    }

    fun clear() {
        disposable.clear()
    }

    fun onRefresh() {
        totalCount = 0
        isLoaded = false
        disposable.clear()
        onZeroItemsLoaded()
    }

}
