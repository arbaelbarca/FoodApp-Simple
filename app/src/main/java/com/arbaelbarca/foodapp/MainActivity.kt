package com.arbaelbarca.foodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arbaelbarca.foodapp.ui.activity.FrameLayoutActivity
import com.arbaelbarca.foodapp.ui.activity.LoginActivity
import com.arbaelbarca.foodapp.utils.intentPage

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        finish()
        intentPage(this, LoginActivity::class.java)
    }
}