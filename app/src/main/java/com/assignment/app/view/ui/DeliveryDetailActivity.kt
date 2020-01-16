package com.assignment.app.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.assignment.app.R
import com.assignment.app.databinding.ActivityDeliveryDetailBinding
import com.assignment.app.utils.DELIVERY_DATA
import com.assignment.app.viewmodel.DeliveryListViewModel
import com.assignment.app.viewmodel.ViewModelFactory

class DeliveryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeliveryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delivery_detail)
        binding.deliveryData = intent.getParcelableExtra(DELIVERY_DATA)




    }
}
