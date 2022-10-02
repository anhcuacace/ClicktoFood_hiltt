package com.tunanh.clicktofood_hilt.ui.custemview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.databinding.LayoutActionBarBinding
import com.tunanh.clicktofood_hilt.util.setOnSingClickListener

class ActionBar(context: Context?,attrs:AttributeSet?):RelativeLayout(context,attrs) {
private val binding: LayoutActionBarBinding
init {
    binding=LayoutActionBarBinding.inflate(LayoutInflater.from(context))
    addView(binding.root)

    val typedArray= context?.obtainStyledAttributes(attrs, R.styleable.ActionBar)
    val title= typedArray?.getString(R.styleable.ActionBar_action_bar_title)?:""
    binding.title.text=title
    val srcLeft=typedArray?.getResourceId(R.styleable.ActionBar_action_bar_src_left,-1)?:-1
    if (srcLeft!=-1){
        binding.iconLeft.setImageDrawable(ContextCompat.getDrawable(context!!,srcLeft))
    }
    val srcRight=typedArray?.getResourceId(R.styleable.ActionBar_action_bar_src_right,-1)?:-1
    if (srcRight!=-1){
        binding.iconRight.setImageDrawable(ContextCompat.getDrawable(context!!,srcRight))
    }
    val srcRight2=typedArray?.getResourceId(R.styleable.ActionBar_action_bar_src_right2,-1)?:-1
    if (srcRight2!=-1){
        binding.iconRight2.setImageDrawable(ContextCompat.getDrawable(context!!,srcRight2))
    }
    typedArray?.recycle()
}
    fun setTitle(title:String){
        binding.title.text=title
    }
    fun setOnClickImageLeft(callback:(()->Unit)){
        binding.iconLeft.setOnSingClickListener {
            callback.invoke()
        }
    }
    fun setOnClickImageRight(callback:(()->Unit)){
        binding.iconRight.setOnSingClickListener {
            callback.invoke()
        }
    }
    fun setOnClickImageRight2(callback:(()->Unit)){
        binding.iconRight2.setOnSingClickListener {
            callback.invoke()
        }
    }
}