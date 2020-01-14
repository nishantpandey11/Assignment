package com.assignment.app.view.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.assignment.app.R
import com.assignment.app.databinding.ActivityMainBinding
import com.assignment.app.di.ViewModelFactory
import com.assignment.app.service.model.Delivery
import com.assignment.app.view.adapter.DeliveryListAdapter
import com.assignment.app.view.callback.ItemClickCallback
import com.assignment.app.viewmodel.DeliveryListViewModel
import com.google.android.material.snackbar.Snackbar

class DeliveryListActivity : AppCompatActivity(), ItemClickCallback {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var deliveryAdapter: DeliveryListAdapter
    private lateinit var viewModel: DeliveryListViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(this))
            .get(DeliveryListViewModel::class.java)

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null)
                showError(errorMessage)
            else {
                hideError()
            }
        })



        viewModel.itemClick.observe(this, Observer {
            Toast.makeText(this, it.route.start, Toast.LENGTH_SHORT).show()
            Log.e("click--->", it.toString())
            Log.e("price-->", "" + it.getPrice())
        })

        mainBinding.swipeRefresh.setOnRefreshListener(object :
            SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                //todo delete all data from db and refresh
                viewModel.refreshUI()
            }

        })
        viewModel.reloadTrigger.observe(this, Observer {
            if (it) mainBinding.swipeRefresh.isRefreshing = false
        })

        viewModel.deliveryList.observe(this, Observer {
            val deliveryListAdapter: DeliveryListAdapter =
                mainBinding.recyclerView.adapter as DeliveryListAdapter
            deliveryListAdapter.submitList(it)
            deliveryListAdapter.setOnClickListener(this)
        })


        mainBinding.viewModel = viewModel
    }


    private fun showError(errorMessage: Int) {
        errorSnackbar = Snackbar.make(mainBinding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    override fun onItemClick(delivery: Delivery) {
        Log.e("fav-->", "" + delivery.isFavorite)

        Toast.makeText(this, delivery.route.start, Toast.LENGTH_SHORT).show()
        //Log.e("click--->", delivery.toString())
       // Log.e("price-->", "" + delivery.getPrice())
        delivery.isFavorite = true
        Log.e("fav-->", "" + delivery.toString())
        viewModel.setFav(delivery)
        Log.e("fav-->", "" + delivery.isFavorite)
    }
}
