package com.habibi.storyapp.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.habibi.storyapp.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : AppCompatActivity() {

    private var binding: ActivityAuthenticationBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
}