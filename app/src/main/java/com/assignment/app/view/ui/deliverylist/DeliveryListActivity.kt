package com.assignment.app.view.ui.deliverylist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.assignment.app.R
import com.assignment.app.databinding.ActivityMainBinding
import com.assignment.app.view.adapter.DeliveryListAdapter
import com.assignment.app.view.callback.ItemClickCallback
import com.google.android.material.snackbar.Snackbar

class DeliveryListActivity : AppCompatActivity(), ItemClickCallback {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var deliveryAdapter: DeliveryListAdapter
    private lateinit var viewModel: DeliveryListViewModel
    private var errorSnackbar: Snackbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //var deliveryAdapter = DeliveryListAdapter()
        //deliveryAdapter.setOnClickListener(this)
        //mainBinding.recyclerView.adapter = deliveryAdapter

        viewModel = ViewModelProviders.of(this).get(DeliveryListViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null)
                showError(errorMessage)
            else {
                hideError()
            }
        })
        mainBinding.viewModel = viewModel


        /*val delivery = Delivery("123","Good","15:00",
               "http://loremflickr.com/320/240/cat?lock=56004","$45","$3",
               Route("Gurgaon","Delhi"),
               Sender("8447164064","Nishant","np@a.com")
           )
          val delivery1 = Delivery("1234","Good","15:00",
              "https://loremflickr.com/320/240/cat?lock=56004","$45","$3",
              Route("Gurgaon","Delhi"),
              Sender("8447164064","Nishant","np@a.com")
          )
          val delivery2 = Delivery("1235","Good","15:00",
              "https://loremflickr.com/320/240/cat?lock=56004","$45","$3",
              Route("Gurgaon","Delhi"),
              Sender("8447164064","Nishant","np@a.com")
          )
          val delivery3 = Delivery("1236","Good","15:00",
              "https://loremflickr.com/320/240/cat?lock=56004","$45","$3",
              Route("Gurgaon","Delhi"),
              Sender("8447164064","Nishant","np@a.com")
          )
          Log.e("d====>",String.format(getString(R.string.price),delivery.getPrice()))

          val list= ArrayList<Delivery>();
          list.add(delivery)
          list.add(delivery1)
          list.add(delivery2)
          list.add(delivery3)
          deliveryAdapter.setDelivery(list)*/


        // mainBinding.delivery = delivery;

    }

    override fun onItemClick() {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
    }

    private fun showError(errorMessage: Int) {
        errorSnackbar = Snackbar.make(mainBinding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }
}
