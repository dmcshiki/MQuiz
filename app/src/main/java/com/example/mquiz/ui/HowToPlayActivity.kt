package com.example.mquiz.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mquiz.R
import com.example.mquiz.databinding.ActivityHomeBinding
import com.example.mquiz.databinding.ActivityHowToPlayBinding
import com.google.firebase.firestore.FirebaseFirestore

class HowToPlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHowToPlayBinding
    private lateinit var firebaseStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHowToPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar!!.title = "Como jogar"
        actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

