package com.tunanh.clicktofood_hilt.ui.home.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.data.local.model.Food
import com.tunanh.clicktofood_hilt.databinding.ItemCartBinding
import com.tunanh.clicktofood_hilt.util.setOnSingClickListener

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    inner class CartViewHolder constructor(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
            Glide.with(itemView.context).load(food.img).error(R.mipmap.ic_launcher)
                .into(binding.imgFood)
            binding.tvTitleItem.text = food.title
            binding.cost.text = "${food.cost} $"
            binding.count.text = food.amount.toString()
            binding.tvRateCount.text = food.star.toString()
        }
    }

    var itemFood: ArrayList<Food>? = null

    var onClickPlus: ((Food,Int) -> Unit)? = null
    var onClickMinus: ((Food, Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        itemFood?.let { holder.bind(it[position]) }

        holder.binding.plus.setOnSingClickListener {

            itemFood!![position] = itemFood!![position].also {
                it.amount = it.amount + 1
            }
            itemFood?.let { it1 -> onClickPlus?.invoke(it1[position], position) }
        }
        holder.binding.minus.setOnSingClickListener {
            itemFood!![position] = itemFood!![position].also {
                it.amount = it.amount - 1
            }
            itemFood?.let { it1 -> onClickMinus?.invoke(it1[position], position) }
        }
    }

    override fun getItemCount(): Int {
        return itemFood?.size ?: 0
    }
}