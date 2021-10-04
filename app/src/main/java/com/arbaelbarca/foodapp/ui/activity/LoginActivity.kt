package com.arbaelbarca.foodapp.ui.activity

import android.view.LayoutInflater
import com.arbaelbarca.foodapp.basedata.BaseActivityBinding
import com.arbaelbarca.foodapp.databinding.ActivityLoginBinding
import com.arbaelbarca.foodapp.utils.intentPageData
import com.arbaelbarca.foodapp.utils.showToast

class LoginActivity : BaseActivityBinding<ActivityLoginBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityLoginBinding
        get() = ActivityLoginBinding::inflate

    override fun setupView(binding: ActivityLoginBinding) {
        initial(binding)
    }

    private fun initial(binding: ActivityLoginBinding) {
        binding.btnSubmit.setOnClickListener {
            val regexStr = "\\+62\\s\\d{3}[-\\.\\s]??\\d{3}[-\\.\\s]??\\d{3,4}|\\(0\\d{2,3}\\)\\s?\\d+|0\\d{2,3}\\s?\\d{6,7}|\\+62\\s?361\\s?\\d+|\\+62\\d+|\\+62\\s?(?:\\d{3,}-)*\\d{3,5}\n"
            val regex = Regex(regexStr)
            val textPhone = binding.edNumberPhone.text.toString()

            if (textPhone.isEmpty()) {
                showToast("Phone not available", this)
            } else if (textPhone.length < 10 || textPhone.length > 13 || textPhone.matches(regex)) {
                showToast("Please enter valid phone number", this)
            } else {
                val intent = intentPageData(this, FrameLayoutActivity::class.java)
                    .putExtra("pos", 1)
                startActivity(intent)
            }
        }
    }
}