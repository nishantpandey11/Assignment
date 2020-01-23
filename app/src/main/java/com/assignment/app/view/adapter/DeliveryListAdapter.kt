package com.assignment.app.view.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.assignment.app.R
import com.assignment.app.data.model.Delivery
import com.assignment.app.databinding.DeliveryListItemBinding
import com.assignment.app.view.callback.ItemClickCallback

class DeliveryListAdapter :
    PagedListAdapter<Delivery, DeliveryListAdapter.DeliveryViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Delivery> =
            object : DiffUtil.ItemCallback<Delivery>() {
                override fun areItemsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
                    return oldItem.id == newItem.id && oldItem.isFavorite == newItem.isFavorite
                }
            }
    }


    private var itemClickCallback: ItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val itemBinding: DeliveryListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context)
            , R.layout.delivery_list_item, parent, false
        )
        itemBinding.callback = itemClickCallback
        return DeliveryViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        val currentDelivery: Delivery? = getItem(position)
        holder.bind(currentDelivery)
    }



    fun setOnClickListener(listener: ItemClickCallback) {
        itemClickCallback = listener
    }

    inner class DeliveryViewHolder(private val itemBinding: DeliveryListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemView.setOnClickListener {
                itemClickCallback?.onItemClick(getItem(adapterPosition)!!)
            }
        }

        fun bind(delivery: Delivery?) {
            itemBinding.viewModel = delivery

        }

    }

}