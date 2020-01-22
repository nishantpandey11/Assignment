package com.assignment.app.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.assignment.app.data.DeliveryRepository
import com.assignment.app.data.model.Delivery
import com.assignment.app.data.source.local.DeliveryBoundaryCallback
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class DeliveryListViewModel @Inject constructor(
    private val repository: DeliveryRepository
) : ViewModel() {
    private var subscription: Disposable? = null
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { boundaryCallback.retry() }
    private var boundaryCallback: DeliveryBoundaryCallback
    val reloadTrigger = MutableLiveData<Boolean>()
    var liveData: LiveData<PagedList<Delivery>>


    init {
        liveData = repository.getPagedData()
        boundaryCallback = repository.deliveryBoundaryCallback
        boundaryCallback.setNetworkListener(errorMessage)
    }


    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }


    fun refreshUI() {
        boundaryCallback.onRefresh(reloadTrigger)
    }

    fun setFav(delivery: Delivery?) {
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