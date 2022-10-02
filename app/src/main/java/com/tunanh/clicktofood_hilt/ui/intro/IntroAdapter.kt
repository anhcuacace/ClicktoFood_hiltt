package com.tunanh.clicktofood_hilt.ui.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tunanh.clicktofood_hilt.data.local.model.IntroItem
import com.tunanh.clicktofood_hilt.databinding.ItemIntroBinding
import com.tunanh.clicktofood_hilt.util.setOnSingClickListener

class IntroAdapter : RecyclerView.Adapter<IntroAdapter.IntroViewHolder>() {
    inner class IntroViewHolder constructor(private val binding: ItemIntroBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: IntroItem) {
            binding.title.text = item.title
            binding.description.text = item.description
            binding.introImg.setImageResource(item.img)
        }
    }

    var itemtList: List<IntroItem>? = null
    var onItemClick: ((IntroItem, Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        val binding = ItemIntroBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return IntroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        itemtList?.get(position)?.let { holder.bind(it) }
        holder.itemView.setOnSingClickListener {
            itemtList?.let { it1 -> onItemClick?.invoke(it1[position], position) }
        }
    }


    override fun getItemCount(): Int = itemtList?.size ?: 0

}