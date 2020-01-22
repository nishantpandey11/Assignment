package com.assignment.app.data.source.local

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.assignment.app.R
import com.assignment.app.data.model.Delivery
import com.assignment.app.data.source.network.ApiInterface
import com.assignment.app.utils.LIMIT
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DeliveryBoundaryCallback  @Inject constructor(
    private val apiInterface: ApiInterface,
    private val deliveryDao: DeliveryDao
) : PagedList.BoundaryCallback<Delivery>() {
    private var totalCount: Int = 0
    private var isLoaded: Boolean = false
    private var fromSwipeToRefresh: Boolean = false
    private var disposable = CompositeDisposable()
    private lateinit var reloadTrigger : MutableLiveData<Boolean>
    private var errorMessage: MutableLiveData<Int> = MutableLiveData()


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

    @SuppressLint("CheckResult")
    fun fetchDeliveries(offset: Int, pageSize: Int) {
        errorMessage.value = null
        Log.e("PK", "offset: $offset, pageSize: $pageSize")
        // todo need to add network check
        /*if (!NetworkUtility.isInternetAvailable()) {
            return
        }*/

        apiInterface.getDeliveryList(offset, pageSize).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> success(result) }, { error -> error(error) })

    }

    @SuppressLint("CheckResult")
    private fun success(list: List<Delivery>?) {
        if (list == null) {
            isLoaded = true
            Log.e("fetchDeliveries success", "nothing to show")
        } else {
            totalCount += list.size
            if (list.size < LIMIT) {
                isLoaded = true
                Log.e("fetchDeliveries success", "eof")
            }
            Completable.complete().subscribeOn(Schedulers.io())
                .subscribe {
                    if(fromSwipeToRefresh){
                        deliveryDao.deleteAll()
                        reloadTrigger.postValue(true)
                        fromSwipeToRefresh = false
                    }
                    deliveryDao.insertAll(list)
                }
        }
    }

    private fun error(throwable: Throwable?) {
        throwable?.let {
            throwable.printStackTrace()
            Log.e("fetchDeliveries error", throwable.message.toString())
            errorMessage.value = R.string.post_error
            if(fromSwipeToRefresh){
                reloadTrigger.value = true
            }
        }
    }


    fun retry() {
        fetchDeliveries(totalCount, LIMIT)
    }


    fun onRefresh(reloadTrigger: MutableLiveData<Boolean>) {
        this.reloadTrigger = reloadTrigger
        fromSwipeToRefresh = true
        totalCount = 0
        isLoaded = false
        disposable.clear()
        onZeroItemsLoaded()
    }

    fun setNetworkListener(errorMessage: MutableLiveData<Int>) {
        this.errorMessage = errorMessage

    }

}
