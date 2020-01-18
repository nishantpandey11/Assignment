package com.assignment.app.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.assignment.app.R
import com.assignment.app.databinding.ActivityDeliveryDetailBinding
import com.assignment.app.utils.DELIVERY_DATA
import com.assignment.app.viewmodel.DeliveryListViewModel
import com.assignment.app.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class DeliveryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeliveryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delivery_detail)
        binding.deliveryData = intent.getParcelableExtra(DELIVERY_DATA)
        binding.toolbar.tv_tile.text = getString(R.string.title_delivery_details)
        val ivBack = binding.toolbar.iv_back
        ivBack.visibility = View.VISIBLE
        ivBack.setOnClickListener { super.onBackPressed() }



    }
}
