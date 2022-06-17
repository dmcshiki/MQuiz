package com.example.mquiz.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.mquiz.MainActivity
import com.example.mquiz.R
import com.example.mquiz.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private lateinit var actionBar: ActionBar

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar = supportActionBar!!
        actionBar.title = "Matemaquiz"

        firebaseAuth = FirebaseAuth.getInstance()

        binding.startButton.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }

        binding.rulesButton.setOnClickListener {
            startActivity(Intent(this, HowToPlayActivity::class.java))
        }
        binding.leaderboardButton.setOnClickListener {
            startActivity(Intent(this, LeaderboardActivity::class.java))
        }
        binding.logoutButton.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}