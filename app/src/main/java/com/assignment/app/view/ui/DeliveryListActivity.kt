package com.assignment.app.view.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.assignment.app.R
import com.assignment.app.data.model.Delivery
import com.assignment.app.databinding.ActivityMainBinding
import com.assignment.app.utils.DELIVERY_DATA
import com.assignment.app.view.adapter.DeliveryListAdapter
import com.assignment.app.view.callback.ItemClickCallback
import com.assignment.app.viewmodel.DeliveryListViewModel
import com.assignment.app.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class DeliveryListActivity : AppCompatActivity(), ItemClickCallback {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var viewModel: DeliveryListViewModel
    private var errorSnackbar: Snackbar? = null
    private val deliveryListAdapter: DeliveryListAdapter = DeliveryListAdapter()
    private val requestCode = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainBinding.toolbar.tv_tile.text = getString(R.string.title_delivery)

        mainBinding.recyclerView.adapter = deliveryListAdapter
        deliveryListAdapter.setOnClickListener(this)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(this))
            .get(DeliveryListViewModel::class.java)

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null)
                showError(errorMessage)
            else {
                hideError()
            }
        })

        mainBinding.swipeRefresh.setOnRefreshListener { viewModel.refreshUI() }

        viewModel.reloadTrigger.observe(this, Observer {
            if (it) mainBinding.swipeRefresh.isRefreshing = false
        })

        viewModel.liveData.observe(this, Observer<PagedList<Delivery>> { pagedList ->
            deliveryListAdapter.submitList(pagedList)
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
        val intent = Intent(this, DeliveryDetailActivity::class.java)
        intent.putExtra(DELIVERY_DATA, delivery)
        startActivityForResult(intent,requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == requestCode && resultCode == Activity.RESULT_OK && data != null) {
            val delivery = data.getParcelableExtra<Delivery>(DELIVERY_DATA)
            viewModel.setFav(delivery)

        }

    }
}
