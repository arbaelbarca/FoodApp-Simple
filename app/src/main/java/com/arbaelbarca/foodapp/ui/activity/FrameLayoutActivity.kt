package com.arbaelbarca.foodapp.ui.activity

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.arbaelbarca.foodapp.R
import com.arbaelbarca.foodapp.basedata.BaseActivityBinding
import com.arbaelbarca.foodapp.databinding.ActivityFrameLayoutBinding
import com.arbaelbarca.foodapp.ui.fragment.FragmentMenuPasta
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FrameLayoutActivity : BaseActivityBinding<ActivityFrameLayoutBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityFrameLayoutBinding
        get() = ActivityFrameLayoutBinding::inflate

    override fun setupView(binding: ActivityFrameLayoutBinding) {
        initial(binding)
    }

    private fun initial(binding: ActivityFrameLayoutBinding) {
        setFragment(binding, 1)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    fun setFragment(binding: ActivityFrameLayoutBinding, pos: Int) {
        when (pos) {
            1 -> {
                val fragmentMenuPasta = FragmentMenuPasta.newInstance("", "")
                gotoFragment(fragmentMenuPasta)
            }
        }
    }

    private fun gotoFragment(fragment: Fragment) {
        return loadFragment(fragment)
    }


    private fun loadFragment(fragment: Fragment?) {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit()
        }
    }
}