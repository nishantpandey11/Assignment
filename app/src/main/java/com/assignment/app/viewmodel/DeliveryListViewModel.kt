package com.assignment.app.viewmodel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.assignment.app.R
import com.assignment.app.base.BaseViewModel
import com.assignment.app.service.model.Delivery
import com.assignment.app.service.network.ApiInterface
import com.assignment.app.view.adapter.DeliveryListAdapter
import com.assignment.app.view.callback.ItemClickCallback
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DeliveryListViewModel : BaseViewModel(),ItemClickCallback {
    @Inject
    lateinit var apiInterface: ApiInterface
    private lateinit var subscription: Disposable
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadDeliveries() }
    val deliveryListAdapter:DeliveryListAdapter = DeliveryListAdapter()
    val itemClick: MutableLiveData<Delivery> = MutableLiveData()
    lateinit var callback:ItemClickCallback

    init {
        loadDeliveries()
    }

    override fun onCleared(){
        super.onCleared()
        subscription.dispose()
    }

    private fun loadDeliveries() {
          apiInterface.getDeliveryList(0, 20)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            /*.doOnSubscribe { onRetrieveDeliveryListStart() }
              .doOnTerminate { onRetrieveDeliveryListFinish() }
              .subscribe(
                  { result -> onRetrieveDeliveryListSuccess(result) },
                  { onRetrieveDeliveryListError() }
              )*/
            .subscribe(object: Observer<List<Delivery>>{
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
        deliveryListAdapter.submitList(delivery)
        deliveryListAdapter.setOnClickListener(this)
    }

    private fun onRetrieveDeliveryListError() {
        errorMessage.value = R.string.post_error
    }

    override fun onItemClick(delivery: Delivery?) {
        itemClick.value = delivery
    }

}