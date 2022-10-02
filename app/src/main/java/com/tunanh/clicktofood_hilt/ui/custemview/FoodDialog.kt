package com.tunanh.clicktofood_hilt.ui.custemview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.databinding.LayoutConfirmDialogBinding
import com.tunanh.clicktofood_hilt.listener.OnClickConfirmDialog

class FoodDialog private constructor(builder: Builder) {
    private var title: String? = null
    private var content: String? = null
    private var rightButton: String? = null
    private var leftButton: String? = null
    private var isCancelable: Boolean = true
    private var context: Context? = null
    private var onClickListener: OnClickConfirmDialog? = null
    private var alertDialog: AlertDialog? = null
    private var isShow = false

    init {
        this.title = builder.getTitle()
        this.content = builder.getContent()
        this.rightButton = builder.getRightButton()
        this.leftButton = builder.getLeftButton()
        this.isCancelable = builder.isCancelable()
        this.onClickListener = builder.getOnClickListener()
        this.context = builder.getContext()
    }

    private fun show() {
        if (alertDialog != null && (isShow || alertDialog!!.isShowing)) {
            isShow = false
            alertDialog!!.dismiss()
        }
        context?.let {
            isShow = true
            val dialogBuilder = AlertDialog.Builder(it, R.style.DefautDialogStyle)
            val binding: LayoutConfirmDialogBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.layout_confirm_dialog,
                null,
                false
            )
            dialogBuilder.setView(binding.root)
            alertDialog = dialogBuilder.create()
            alertDialog!!.setCancelable(isCancelable)
            alertDialog!!.setCanceledOnTouchOutside(isCancelable)
            alertDialog!!.window!!.setBackgroundDrawableResource(R.color.transparentColor)
            binding.tvTitle.text = title
            binding.tvTitle.visibility = if (title.isNullOrEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
            binding.tvContent.text = content
            binding.tvContent.visibility = if (content.isNullOrEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
            binding.btnAccept.setOnClickListener {
                onClickListener!!.onClickRightButton()
                isShow = false
                alertDialog!!.dismiss()
            }
            binding.btnAccept.text = rightButton
            binding.btnAccept.visibility = if (rightButton.isNullOrEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
            binding.btnCancel.text = leftButton
            binding.btnCancel.visibility = if (leftButton.isNullOrEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
            binding.btnCancel.setOnClickListener {
                onClickListener!!.onClickLeftButton()
                isShow = false
                alertDialog!!.dismiss()
            }

            if (title!!.isNotEmpty()) {
                binding.tvTitle.visibility = View.VISIBLE
            } else {
                binding.tvTitle.visibility = View.GONE
            }
            alertDialog!!.show()
        }
    }

    class Builder {
        private var title: String = ""
        private var content: String = ""
        private var rightButton: String = ""
        private var leftButton: String = ""
        private var isCancelable: Boolean = true
        private var context: Context? = null
        private var onClickListener: OnClickConfirmDialog? = null

        fun getTitle(): String {
            return title
        }

        fun getContent(): String {
            return content
        }

        fun getRightButton(): String {
            return rightButton
        }

        fun getLeftButton(): String {
            return leftButton
        }

        fun isCancelable(): Boolean {
            return isCancelable
        }

        fun getOnClickListener(): OnClickConfirmDialog {
            return onClickListener!!
        }

        fun getContext(): Context {
            return context!!
        }

        fun with(context: Context) = apply { this.context = context }
        fun title(title: String) = apply { this.title = title }
        fun content(content: String) = apply { this.content = content }
        fun isCancelable(isCancelable: Boolean) = apply { this.isCancelable = isCancelable }
        fun rightButton(rightButton: String) = apply { this.rightButton = rightButton }
        fun leftButton(leftButton: String) = apply { this.leftButton = leftButton }
        fun onClick(onClickListener: OnClickConfirmDialog) =
            apply { this.onClickListener = onClickListener }

        fun build() = FoodDialog(this).show()

    }
}