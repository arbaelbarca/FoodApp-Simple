package com.arbaelbarca.foodapp.basedata

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.arbaelbarca.foodapp.ui.dialog.CustomProgressDialog

abstract class BaseFragmentBinding<T : ViewBinding> : Fragment() {

    private var _binding: T? = null
    private val binding get() = _binding!!
    var customProgressDialog: CustomProgressDialog? = null

    var latitude = 0.0
    var longitude = 0.0

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customProgressDialog = CustomProgressDialog(context as Context)
        setupView(binding)
    }


    abstract fun setupView(binding: T)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun showDialog(boolean: Boolean) {
        if (boolean) {
            customProgressDialog?.show()
        } else {
            customProgressDialog?.dismiss()
        }
    }


}