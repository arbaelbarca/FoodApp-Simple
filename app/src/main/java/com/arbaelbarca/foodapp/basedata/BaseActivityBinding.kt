package com.arbaelbarca.foodapp.basedata

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.arbaelbarca.foodapp.ui.dialog.CustomProgressDialog

abstract class BaseActivityBinding<T : ViewBinding> : AppCompatActivity() {
    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> T

    //    var layoutToolbarV1Binding: LayoutToolbarV1Binding? = null
    var customProgressDialog: CustomProgressDialog? = null

    var latitude = 0.0
    var longitude = 0.0


    @Suppress("UNCHECKED_CAST")
    protected val binding: T
        get() = _binding as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        customProgressDialog = CustomProgressDialog(this)
        setContentView(requireNotNull(_binding).root)
        setupView(binding)
    }

    fun showDialog(boolean: Boolean) {
        if (boolean) {
            customProgressDialog?.show()
        } else {
            customProgressDialog?.dismiss()
        }
    }


    abstract fun setupView(binding: T)

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}