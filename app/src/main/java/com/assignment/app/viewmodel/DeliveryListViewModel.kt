package com.assignment.app.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.assignment.app.R
import com.assignment.app.base.BaseViewModel
import com.assignment.app.data.DeliveryRepository
import com.assignment.app.data.model.Delivery
import com.assignment.app.data.source.local.DeliveryDao
import com.assignment.app.data.source.network.ApiInterface
import com.assignment.app.view.adapter.DeliveryListAdapter
import com.assignment.app.view.callback.ItemClickCallback
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class DeliveryListViewModel constructor(
    private val deliveryDao: DeliveryDao,
    private val repository: DeliveryRepository
) : BaseViewModel(), ItemClickCallback {
    @Inject
    lateinit var apiInterface: ApiInterface
    private lateinit var subscription: Disposable
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadDeliveries() }
    val deliveryListAdapter: DeliveryListAdapter = DeliveryListAdapter()
    val itemClick: MutableLiveData<Delivery> = MutableLiveData()
    lateinit var callback: ItemClickCallback

    val deliveryList: MutableLiveData<List<Delivery>> = MutableLiveData<List<Delivery>>()

    val reloadTrigger = MutableLiveData<Boolean>()


    init {
        loadDeliveries()
    }


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun refreshUI() {
        Completable.fromAction { deliveryDao.deleteAll() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {
                    Log.e("oncomplete", "complete")
                    loadDeliveries()
                }

                override fun onError(e: Throwable) {
                }
            })


    }

    fun setFav(delivery: Delivery) {
        Completable.fromAction { deliveryDao.updateDelivery(delivery) }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {
                    Log.e("oncomplete", "setfav")
                    //onRetrieveDeliveryListSuccess(delivery)
                }

                override fun onError(e: Throwable) {
                }
            })
    }

    fun loadDeliveries() {
        repository.getDeliveries(apiInterface)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<Delivery>> {
                override fun onComplete() {
                    onRetrieveDeliveryListFinish()
                }

                override fun onSubscribe(d: Disposable) {
                    subscription = d
                    onRetrieveDeliveryListStart()

                }

                override fun onNext(t: List<Delivery>) {
                    onRetrieveDeliveryListSuccess(t)
                }

                override fun onError(e: Throwable) {
                    onRetrieveDeliveryListError()
                }

            })
    }

    private fun onRetrieveDeliveryListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null

    }

    private fun onRetrieveDeliveryListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveDeliveryListSuccess(delivery: List<Delivery>) {
        //  deliveryListAdapter.submitList(delivery)
        //deliveryListAdapter.setOnClickListener(this)
        reloadTrigger.value = true
        deliveryList.value = delivery


    }

    private fun onRetrieveDeliveryListError() {
        errorMessage.value = R.string.post_error
        reloadTrigger.value = true
    }

    override fun onItemClick(delivery: Delivery) {
        //itemClick.value = delivery

        //todo remove click callback from here
    }

}