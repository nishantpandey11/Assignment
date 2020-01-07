package com.assignment.app.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.assignment.app.R
import com.assignment.app.databinding.ActivityMainBinding
import com.assignment.app.service.model.Delivery
import com.assignment.app.service.model.Route
import com.assignment.app.service.model.Sender
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
}
