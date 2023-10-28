package com.fajar.submissionstoryapp.features.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fajar.submissionstoryapp.databinding.SplashScreenBinding
import com.fajar.submissionstoryapp.features.utils.Const.time
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private lateinit var spBinding: SplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        spBinding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(spBinding.root)
        val intentToHome = Intent(this@SplashScreen, StoryActivity::class.java)
        val handler = Handler(mainLooper)

        lifecycleScope.launch(Dispatchers.Default) {
            handler.postDelayed({
                startActivity(intentToHome)
                finish()
            }, time.toLong())
        }
    }

}