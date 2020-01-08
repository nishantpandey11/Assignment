package com.assignment.app.view.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assignment.app.R
import com.assignment.app.databinding.DeliveryListItemBinding
import com.assignment.app.service.model.Delivery
import com.assignment.app.view.callback.ItemClickCallback
import com.assignment.app.view.ui.deliverylist.DeliveryViewModel

class DeliveryListAdapter : ListAdapter<Delivery, DeliveryListAdapter.DeliveryViewHolder>(DIFF_CALLBACK) {
    private lateinit var list: List<Delivery>


    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Delivery> =
            object : DiffUtil.ItemCallback<Delivery>() {
                override fun areItemsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
                    return oldItem.id == newItem.id
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
        val currentDelivery : Delivery = list.get(position)
       ///holder.itemBinding. = currentDelivery
        holder.bind(currentDelivery)
        Log.e("===>bind",currentDelivery.id)
    }

    fun getDeliveryAtPosition(position: Int) : Delivery{
        return getItem(position)
    }
    fun setOnClickListener(listener: ItemClickCallback) {
        itemClickCallback = listener
    }

    public fun setDelivery(list: List<Delivery>){
        this.list = list
        notifyDataSetChanged()


    }
    override fun  getItemCount(): Int {
        return if(::list.isInitialized) list.size else 0
    }


    class DeliveryViewHolder(itemBinding: DeliveryListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val itemBinding: DeliveryListItemBinding = itemBinding
        private val viewModel = DeliveryViewModel()

        fun bind(delivery: Delivery){
            viewModel.bind(delivery)
            itemBinding.viewModel = viewModel
        }

    }

}