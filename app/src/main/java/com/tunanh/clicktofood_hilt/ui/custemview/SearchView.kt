package com.tunanh.clicktofood_hilt.ui.custemview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.widget.addTextChangedListener
import com.tunanh.clicktofood_hilt.R

class SearchView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    private var onClickSearch: ((String) -> (Unit))? = null
    private var onClickIconRight: (() -> (Unit))? = null
    private var onClickIconLeft: (() -> (Unit))? = null

    var edtSearch: EditText? = null
    private var ivLeft: ImageView? = null
    private var ivRight: ImageView? = null

    private var rightIcon = -1

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_search_view, this, true)
        edtSearch = findViewById(R.id.edtSearchViewLayoutSearch)
        ivLeft = findViewById(R.id.ivSearchViewLayoutLeft)
        ivRight = findViewById(R.id.ivSearchViewLayoutRight)

        edtSearch?.addTextChangedListener {
            setImageRightResource()
        }
        edtSearch?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onClickSearch?.invoke(edtSearch?.text.toString().trim())
                edtSearch?.clearFocus()
                setImageRightResource()

            }
            false
        }
        ivLeft?.setOnClickListener {
            onClickIconLeft?.invoke()
        }
        ivRight?.setOnClickListener {
            val textSearch = edtSearch?.text.toString().trim()
            if (textSearch.isEmpty()) {
                onClickIconRight?.invoke()
            } else {
                onClickSearch?.invoke("")
                edtSearch?.setText("")
            }
        }

        attrs?.let {
            init(it)
        }
    }

    fun addTextChange(callback: ((String) -> Unit)) {
        edtSearch?.addTextChangedListener {
            callback.invoke(edtSearch?.text.toString().trim())
        }
    }

    fun setTextSearch(text: String) {
        try {
            edtSearch?.setText(text)
            edtSearch?.setSelection(text.length)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getTextSearch(): String {
        return edtSearch?.text.toString().trim()
    }

    fun requestFocusSearch() {
        edtSearch?.requestFocus()
    }

    override fun clearFocus() {
        edtSearch?.clearFocus()
        super.clearFocus()
    }

    private fun init(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchView)

        rightIcon = typedArray.getResourceId(R.styleable.SearchView_search_view_right_icon, -1)
        if (rightIcon != -1) {
            ivRight?.visibility = View.VISIBLE
            ivRight?.setImageResource(rightIcon)
        } else {
            ivRight?.visibility = GONE
        }

        val leftIcon = typedArray.getResourceId(R.styleable.SearchView_search_view_left_icon, -1)
        if (leftIcon != -1) {
            ivLeft?.visibility = View.VISIBLE
            ivLeft?.setImageResource(leftIcon)
        } else {
            ivLeft?.visibility = GONE
        }

        val hint = typedArray.getString(R.styleable.SearchView_search_view_hint) ?: ""
        edtSearch?.hint = hint

        typedArray.recycle()
    }

    private fun setImageRightResource() {
        if (edtSearch?.text.toString().trim().isEmpty()) {
            if (rightIcon != -1) {
                ivRight?.setImageResource(rightIcon)
                ivRight?.visibility = View.VISIBLE
            } else {
                ivRight?.setImageResource(R.drawable.ic_cancel)
                ivRight?.visibility = View.GONE
            }
        } else {
            ivRight?.setImageResource(R.drawable.ic_cancel)
            ivRight?.visibility = View.VISIBLE
        }
    }
}