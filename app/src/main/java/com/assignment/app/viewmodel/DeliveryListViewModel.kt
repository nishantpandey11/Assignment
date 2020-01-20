package com.assignment.app.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.assignment.app.base.BaseViewModel
import com.assignment.app.data.DeliveryRepository
import com.assignment.app.data.model.Delivery
import com.assignment.app.data.source.local.DeliveryBoundaryCallback
import com.assignment.app.data.source.local.DeliveryDao
import com.assignment.app.data.source.network.ApiInterface
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class DeliveryListViewModel constructor(
    private val deliveryDao: DeliveryDao,
    private val repository: DeliveryRepository
) :
    BaseViewModel() {
    @Inject
    lateinit var apiInterface: ApiInterface
    private var subscription: Disposable? = null
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { boundaryCallback.retry() }
    private var boundaryCallback:DeliveryBoundaryCallback
    val reloadTrigger = MutableLiveData<Boolean>()
    var liveData: LiveData<PagedList<Delivery>>


    init {
        boundaryCallback = DeliveryBoundaryCallback(apiInterface, deliveryDao)
        boundaryCallback.setNetworkListener(errorMessage)
        liveData = repository.getPagedData(boundaryCallback)
    }


    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }


    fun refreshUI() {
        boundaryCallback.onRefresh(reloadTrigger)
    }

    fun setFav(delivery: Delivery) {
        repository.setFav(delivery).subscribe(object : CompletableObserver {
            override fun onSubscribe(d: Disposable) {
                subscription = d
            }

            override fun onComplete() {

            }

            override fun onError(e: Throwable) {
            }
        })
    }

}