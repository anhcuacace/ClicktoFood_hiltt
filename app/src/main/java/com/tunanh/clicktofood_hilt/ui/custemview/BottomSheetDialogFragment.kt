package com.tunanh.clicktofood_hilt.ui.custemview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.data.local.model.Food
import com.tunanh.clicktofood_hilt.databinding.BottomSheetBinding
import com.tunanh.clicktofood_hilt.ui.main.MainActivity
import com.tunanh.clicktofood_hilt.util.setOnSingClickListener
import com.tunanh.clicktofood_hilt.util.shareLink

class BottomSheetDialogFragment(var food: Food) : BottomSheetDialogFragment() {

    private var binding: BottomSheetBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetBinding.inflate(LayoutInflater.from(context))
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.imgFood?.let {
            Glide.with(requireContext()).load(food.img).error(R.mipmap.ic_launcher).into(
                it
            )
        }
        updateLove()
        binding?.imgLove?.setOnSingClickListener {
            food = food.also { food.like = !food.like }
            updateLove()
            (activity as MainActivity).viewModel.updateLove(food)
        }
        binding?.tvNameItem?.text = food.title
        binding?.tvPrice?.text = "${food.cost} $"
        binding?.lnShare?.setOnSingClickListener {
            shareLink(
                food.img.toString(),
                requireContext()
            )
        }
        binding?.addToCard?.setOnSingClickListener {
            (activity as MainActivity).viewModel.addToCard(food.also { food.amount+=1 })
            Toast.makeText(
                requireContext(),
                requireContext().getString(R.string.addfood),
                Toast.LENGTH_SHORT
            ).show()
            dismiss()
        }
    }

    private fun updateLove() {
        if (food.like) {
            binding?.imgLove?.setImageResource(R.drawable.ic_love)
            binding?.tvLove?.text = getText(com.facebook.R.string.com_facebook_like_button_liked)
        } else {
            binding?.imgLove?.setImageResource(R.drawable.ic_favorite_border)
            binding?.tvLove?.text =
                getText(com.facebook.R.string.com_facebook_like_button_not_liked)
        }
    }
}