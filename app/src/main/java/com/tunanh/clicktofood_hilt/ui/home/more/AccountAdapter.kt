package com.tunanh.clicktofood_hilt.ui.home.more

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tunanh.clicktofood_hilt.databinding.ItemRclvMoreBinding
import com.tunanh.clicktofood_hilt.util.setOnSingClickListener

class AccountAdapter : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {
    inner class AccountViewHolder constructor(private val binding: ItemRclvMoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemMore: ItemMore) {
            binding.imageItem.setImageResource(itemMore.img)
            binding.textItemAcc.text = itemMore.text
        }
    }

    var itemMore: List<ItemMore>? = null
    var onClickItem: ((Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val binding =
            ItemRclvMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        itemMore?.let { holder.bind(it[position]) }
        holder.itemView.setOnSingClickListener {
            onClickItem?.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return itemMore?.size ?: 0
    }
}