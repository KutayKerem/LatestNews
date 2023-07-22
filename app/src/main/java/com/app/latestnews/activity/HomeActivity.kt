package com.app.latestnews.activity

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.latestnews.databinding.HomeActivityBinding
import com.app.latestnews.pages.lightStatusBar
import com.app.latestnews.pages.setFullScreen

class HomeActivity : AppCompatActivity() {
    lateinit var binding :HomeActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)



        try {
            when (this.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    setFullScreen(window)
                    lightStatusBar(window, false, false) // durum çubuğunu karanlık moda ayarlar

                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    setFullScreen(window)
                    lightStatusBar(window, true, false) // durum çubuğunu karanlık moda ayarlar
                }
                Configuration.UI_MODE_NIGHT_UNDEFINED -> {

                }
            }


        }catch (e:Exception){

        Log.d("Error:",e.localizedMessage)
        }







    }















}