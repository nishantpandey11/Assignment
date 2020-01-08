package com.assignment.app.view.ui.deliverylist

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.assignment.app.R
import com.assignment.app.base.BaseViewModel
import com.assignment.app.service.model.Delivery
import com.assignment.app.service.network.ApiInterface
import com.assignment.app.view.adapter.DeliveryListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DeliveryListViewModel : BaseViewModel() {
    @Inject
    lateinit var apiInterface: ApiInterface
    private lateinit var subscription: Disposable
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadDeliveries() }
    val deliveryListAdapter:DeliveryListAdapter = DeliveryListAdapter()

    init {
        loadDeliveries()
    }

    override fun onCleared(){
        super.onCleared()
        subscription.dispose()
    }

    private fun loadDeliveries() {
        subscription = apiInterface.getDeliveryList(0, 20)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveDeliveryListStart() }
            .doOnTerminate { onRetrieveDeliveryListFinish() }
            .subscribe(
                { result -> onRetrieveDeliveryListSuccess(result) },
                { onRetrieveDeliveryListError() }

            )

    }

    private fun onRetrieveDeliveryListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null

    }

    private fun onRetrieveDeliveryListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveDeliveryListSuccess(delivery: List<Delivery>) {
        deliveryListAdapter.setDelivery(delivery)
        //Log.e("===>",delivery[0].id+"")
        for (item in delivery){
            Log.e("===>",item.id+"")
        }
    }

    private fun onRetrieveDeliveryListError() {
        errorMessage.value = R.string.post_error
    }
}