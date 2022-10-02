package com.tunanh.clicktofood_hilt.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.tunanh.clicktofood_hilt.ui.custemview.FoodDialog
import com.tunanh.clicktofood_hilt.ui.main.MainActivity
import com.tunanh.clicktofood_hilt.ui.main.MainViewModel
import com.tunanh.clicktofood_hilt.listener.OnClickConfirmDialog

abstract class BaseFragment<T : ViewDataBinding> : Fragment(){
    protected lateinit var binding: T
    protected abstract val viewModel: ViewModel
    protected val mainViewModel by viewModels<MainViewModel>()


    @LayoutRes
    protected abstract fun layoutRes(): Int

    protected abstract fun initView()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner


        initView()
    }

    protected fun showDialogErrorNetwork(){
        showDialog("Thông báo",
            "Hiện tại không có kết nối \n chúng ta gặp lại nhau sau nhé",
            "Đồng ý",
            "Hủy",
            object : OnClickConfirmDialog {
                override fun onClickRightButton() {
                    (activity as MainActivity).finish()
                }

                override fun onClickLeftButton() {
                }

            })
    }
    protected fun Fragment.getNavController(): NavController =
        NavHostFragment.findNavController(this)

    protected fun showDialog(
        title: String,
        content: String,
        rightButton: String,
        leftButton: String,
        onClickConfirmDialog: OnClickConfirmDialog
    ) {
        FoodDialog.Builder()
            .with(requireContext())
            .title(title)
            .content(content)
            .rightButton(rightButton)
            .leftButton(leftButton)
            .onClick(onClickConfirmDialog)
            .build()
    }
}