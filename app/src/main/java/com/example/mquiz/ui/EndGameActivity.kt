package com.example.mquiz.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.mquiz.R
import com.example.mquiz.databinding.ActivityEndGameBinding
import com.google.firebase.firestore.FirebaseFirestore

class EndGameActivity: AppCompatActivity() {
    private lateinit var actionBar: ActionBar
    private lateinit var firebaseStore: FirebaseFirestore
    private lateinit var binding: ActivityEndGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEndGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Fim de jogo!"

        firebaseStore = FirebaseFirestore.getInstance()
        val score = intent.getStringExtra(finalScore)
        val rightAnswer = intent.getStringExtra(rightAnswerScore)
        val wrongAnswer = intent.getStringExtra(wrongAnswerScore)

        val endScore: TextView = findViewById<TextView>(R.id.EndGameScoreLbl)
        endScore.text = "Seu Score final é: $score pontos!"
        val endRightAnswer: TextView = findViewById<TextView>(R.id.RightAnswersLbl)
        endRightAnswer.text = "Você acertou: $rightAnswer perguntas"
        val endWrongAnswer: TextView = findViewById<TextView>(R.id.WrongAnswersLbl)
        endWrongAnswer.text = "Você errou: $wrongAnswer perguntas"

        binding.leaderboardRegisterButton.setOnClickListener{
            registerOnLeaderboard(score.toString())
        }

        binding.homeButton.setOnClickListener {
            finish()
        }
    }


    private fun registerOnLeaderboard(score: String){
        var username = binding.leaderboardRegisterInput.text.toString().trim()
        if(username.isEmpty() or username.isBlank()){
            binding.leaderboardRegisterInput.error = "Por favor, digite o seu nome"
        }
        else {
            binding.leaderboardRegisterButton.isEnabled = false
            binding.leaderboardRegisterButton.isClickable = false
            binding.leaderboardRegisterButton.setBackgroundColor(Color.GRAY)
            binding.leaderboardRegisterButton.setTextColor(Color.WHITE)
            binding.leaderboardRegisterButton.text = "Cadastrado com sucesso! =)"
            val userInfo: MutableMap<String, Any> = HashMap()
            userInfo["username"] = binding.leaderboardRegisterInput.text.toString()
            userInfo["score"] = score.toInt()

            firebaseStore.collection("userInfo").add(userInfo).addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Aee! Você se cadastrou para o nosso ranking!",
                    Toast.LENGTH_SHORT
                ).show()
            }
                .addOnCanceledListener {
                    Toast.makeText(this, "Algo deu errado!", Toast.LENGTH_SHORT).show()
                }
        }
    }
}