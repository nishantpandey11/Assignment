package com.assignment.app.view.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil

import com.assignment.app.R
import com.assignment.app.data.model.Delivery
import com.assignment.app.databinding.ActivityDeliveryDetailBinding
import com.assignment.app.utils.DELIVERY_DATA
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

        val btnSetFav = binding.tvBtn
        binding.rlBtn.setOnClickListener {

            val deliveryDetail: Delivery = binding.deliveryData!!
            deliveryDetail.isFavorite = btnSetFav.text == getString(R.string.add_to_favorite)

            val intent = Intent()
            intent.putExtra(DELIVERY_DATA, deliveryDetail)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }

        ivBack.setOnClickListener { super.onBackPressed() }


    }
}
