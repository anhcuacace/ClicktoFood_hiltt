package com.tunanh.clicktofood_hilt.util

import android.view.View
import com.tunanh.clicktofood_hilt.listener.OnSingleClickListener

fun View.setOnSingClickListener(onClick: (View) -> Unit) {
    setOnClickListener(object : OnSingleClickListener() {
        override fun onSingleClick(view: View) {
            onClick.invoke(view)
        }
    })
}