package com.assignment.app.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.assignment.app.R
import com.assignment.app.databinding.ActivityMainBinding
import com.assignment.app.view.adapter.DeliveryListAdapter
import com.assignment.app.view.callback.ItemClickCallback

class MainActivity : AppCompatActivity(),ItemClickCallback {
    lateinit var mainBinding: ActivityMainBinding;
    lateinit var deliveryAdapter: DeliveryListAdapter;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        var deliveryAdapter = DeliveryListAdapter()
        deliveryAdapter.setOnClickListener(this)
        mainBinding.recyclerView.adapter = deliveryAdapter

       /* val delivery = Delivery("123","Good","15:00",
             "https://loremflickr.com/320/240/cat?lock=56004","$45","$3",
             Route("Gurgaon","Delhi"),
             Sender("8447164064","Nishant","np@a.com")
         )
        Log.e("d====>",String.format(getString(R.string.price),delivery.getPrice()))*/


       // mainBinding.delivery = delivery;

    }

    override fun onItemClick() {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
    }
}
