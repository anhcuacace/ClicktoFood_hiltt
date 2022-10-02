package com.tunanh.clicktofood_hilt.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import com.tunanh.clicktofood_hilt.R

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: T

    protected abstract val viewModel: ViewModel

    private var dialog: AlertDialog? = null
    protected var navHost: NavHostFragment? = null

//    @Inject
//    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    @LayoutRes
    protected abstract fun layoutRes(): Int


    protected abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes())
        binding.lifecycleOwner = this
//        viewModel = ViewModelProvider(this, viewModelFactory)[viewModelClass()]
        initView()
        createDialog()

    }


    private fun createDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.layout_loading, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setCancelable(false)
        dialog = dialogBuilder.create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(true)
    }

    fun showLoading() {
        dialog?.show()
    }

    fun hiddenLoading() {
        dialog?.dismiss()
    }


}