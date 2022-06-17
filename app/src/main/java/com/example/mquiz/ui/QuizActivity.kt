package com.example.mquiz.ui

import Question
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.mquiz.R
import com.example.mquiz.databinding.ActivityQuizBinding
import generateQuestion
import statement

private lateinit var progressDialog: ProgressDialog
const val finalScore = "com.example.mquiz.finalScore"
const val rightAnswerScore = "com.example.mquiz.rightAnswerScore"
const val wrongAnswerScore = "com.example.mquiz.wrongAnswerScore"
var endScore = 0
var rightAnswers = 0
var wrongAnswers = 0
class QuizActivity : AppCompatActivity() {
    private lateinit var actionBar: ActionBar
    private lateinit var binding: ActivityQuizBinding
    private lateinit var rightAnswer: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar = supportActionBar!!
        actionBar.title = "Jogando!"

        endScore = 0
        rightAnswers = 0
        wrongAnswers = 0

        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var question = createQuestion()

        binding.skipButton.setOnClickListener {
            question = createQuestion()
            binding.skipButton.isEnabled = false
            binding.skipButton.isClickable = false
            binding.skipButton.setBackgroundColor(Color.GRAY)
            binding.skipButton.setTextColor(Color.WHITE)

            binding.answer2.isEnabled = true
            binding.answer2.isClickable = true
            binding.answer2.setBackgroundColor(Color.WHITE)
            binding.answer2.setTextColor(Color.parseColor("#6728A7"))
            binding.answer3.isEnabled = true
            binding.answer3.isClickable = true
            binding.answer3.setBackgroundColor(Color.WHITE)
            binding.answer3.setTextColor(Color.parseColor("#6728A7"))

        }

        binding.resignButton.setOnClickListener {
            intent = Intent(this, EndGameActivity::class.java).apply {
                putExtra(finalScore, endScore.toString())
                putExtra(rightAnswerScore, rightAnswers.toString())
                putExtra(wrongAnswerScore, wrongAnswers.toString())
            }
            startActivity(intent)
            finish()
        }

        binding.hintButton.setOnClickListener {
            useHint()
        }

        createTimer(this)

    }

    private fun insertQuestions(question: Question){
        val possibleAnswers = listOf(
            question.result,
            question.result/2,
            question.result/4,
            question.result*2,
            question.result*4
        ).shuffled()

        val questionStatement: TextView = findViewById<TextView>(R.id.questionLbl)
        questionStatement.text = question.statement()
        rightAnswer = question.result.toString()

        binding.answer1.text = possibleAnswers[0].toString()
        binding.answer2.text = possibleAnswers[1].toString()
        binding.answer3.text = possibleAnswers[2].toString()
        binding.answer4.text = possibleAnswers[3].toString()
        binding.answer5.text = possibleAnswers[4].toString()
    }
    private fun createQuestion(){
        val question = generateQuestion()

        insertQuestions(question)

        binding.answer1.setOnClickListener {
            if(binding.answer1.text == question.result.toString()){
                endScore += 1
                rightAnswers += 1
                checkAnswer(question)
            } else {
                endScore -= 1
                wrongAnswers += 1
                checkAnswer(question)
                }
            }
        binding.answer2.setOnClickListener {
            if(binding.answer2.text == question.result.toString()){
                endScore += 1
                rightAnswers += 1
                checkAnswer(question)
            } else {
                endScore -= 1
                wrongAnswers += 1
                checkAnswer(question)
            }
        }
        binding.answer3.setOnClickListener {
            if(binding.answer3.text == question.result.toString()){
                endScore += 1
                rightAnswers += 1
                checkAnswer(question)
            } else {
                endScore -= 1
                wrongAnswers += 1
                checkAnswer(question)
            }
        }
        binding.answer4.setOnClickListener {
            if(binding.answer4.text == question.result.toString()){
                endScore += 1
                rightAnswers += 1
            } else {
                endScore -= 1
                wrongAnswers += 1
                checkAnswer(question)
            }
        }
        binding.answer5.setOnClickListener {
            if(binding.answer5.text == question.result.toString()){
                endScore += 1
                rightAnswers += 1
                checkAnswer(question)
            } else {
                endScore -= 1
                wrongAnswers += 1
                checkAnswer(question)
            }
        }

    }
    private fun enableButtons(){
        binding.answer1.isEnabled = true
        binding.answer1.isClickable = true
        binding.answer2.isEnabled = true
        binding.answer2.isClickable = true
        binding.answer3.isEnabled = true
        binding.answer3.isClickable = true
        binding.answer4.isEnabled = true
        binding.answer4.isClickable = true
        binding.answer5.isEnabled = true
        binding.answer5.isClickable = true
    }
    private fun disableButtons(){
        binding.answer1.isEnabled = false
        binding.answer1.isClickable = false
        binding.answer2.isEnabled = false
        binding.answer2.isClickable = false
        binding.answer3.isEnabled = false
        binding.answer3.isClickable = false
        binding.answer4.isEnabled = false
        binding.answer4.isClickable = false
        binding.answer5.isEnabled = false
        binding.answer5.isClickable = false
    }
    private fun createTimer(context: Context) {
        val timeLeft: TextView = findViewById(R.id.timerLbl) as TextView
        var startTime = 90000.toLong()

        val timer = object: CountDownTimer(startTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft.setText("Tempo restante: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                intent = Intent(context, EndGameActivity::class.java).apply {
                    putExtra(finalScore, endScore.toString())
                    putExtra(rightAnswerScore, rightAnswers.toString())
                    putExtra(wrongAnswerScore, wrongAnswers.toString())
                }
                startActivity(intent)
                finish()
            }
        }
        timer.start()

    }
    private fun checkAnswer(question: Question){

        disableButtons()
        showAnswer(question)

        val progressRunnable = Runnable {
            enableButtons()
            clearColor()
            createQuestion()
        }

        Handler(Looper.getMainLooper()).postDelayed(progressRunnable, 1000)
    }
    private fun useHint(){
        binding.hintButton.isEnabled = false
        binding.hintButton.isClickable = false
        binding.hintButton.setBackgroundColor(Color.GRAY)
        binding.hintButton.setTextColor(Color.WHITE)

        if(binding.answer1.text != rightAnswer
            && binding.answer2.text != rightAnswer){
            binding.answer1.isEnabled = false
            binding.answer1.isClickable = false
            binding.answer1.setBackgroundColor(Color.RED)
            binding.answer1.setTextColor(Color.WHITE)
            binding.answer2.isEnabled = false
            binding.answer2.isClickable = false
            binding.answer2.setBackgroundColor(Color.RED)
            binding.answer2.setTextColor(Color.WHITE)
        } else if(binding.answer1.text != rightAnswer
            && binding.answer3.text != rightAnswer){
            binding.answer1.isEnabled = false
            binding.answer1.isClickable = false
            binding.answer1.setBackgroundColor(Color.RED)
            binding.answer1.setTextColor(Color.WHITE)
            binding.answer3.isEnabled = false
            binding.answer3.isClickable = false
            binding.answer3.setBackgroundColor(Color.RED)
            binding.answer3.setTextColor(Color.WHITE)
        } else if(binding.answer1.text != rightAnswer
            && binding.answer4.text != rightAnswer){
            binding.answer1.isEnabled = false
            binding.answer1.isClickable = false
            binding.answer1.setBackgroundColor(Color.RED)
            binding.answer1.setTextColor(Color.WHITE)
            binding.answer4.isEnabled = false
            binding.answer4.isClickable = false
            binding.answer4.setBackgroundColor(Color.RED)
            binding.answer4.setTextColor(Color.WHITE)
        } else if(binding.answer2.text != rightAnswer
            && binding.answer3.text != rightAnswer){
            binding.answer2.isEnabled = false
            binding.answer2.isClickable = false
            binding.answer2.setBackgroundColor(Color.RED)
            binding.answer2.setTextColor(Color.WHITE)
            binding.answer3.isEnabled = false
            binding.answer3.isClickable = false
            binding.answer3.setBackgroundColor(Color.RED)
            binding.answer3.setTextColor(Color.WHITE)
        } else if(binding.answer2.text != rightAnswer
            && binding.answer4.text != rightAnswer){
            binding.answer2.isEnabled = false
            binding.answer2.isClickable = false
            binding.answer2.setBackgroundColor(Color.RED)
            binding.answer2.setTextColor(Color.WHITE)
            binding.answer4.isEnabled = false
            binding.answer4.isClickable = false
            binding.answer4.setBackgroundColor(Color.RED)
            binding.answer4.setTextColor(Color.WHITE)
        } else if(binding.answer3.text != rightAnswer
            && binding.answer4.text != rightAnswer){
            binding.answer3.isEnabled = false
            binding.answer3.isClickable = false
            binding.answer3.setBackgroundColor(Color.RED)
            binding.answer3.setTextColor(Color.WHITE)
            binding.answer4.isEnabled = false
            binding.answer4.isClickable = false
            binding.answer4.setBackgroundColor(Color.RED)
            binding.answer4.setTextColor(Color.WHITE)
        }
    }
    private fun showAnswer(question: Question){
        if(binding.answer1.text == question.result.toString()){
            binding.answer1.setBackgroundColor(Color.GREEN)
            binding.answer1.setTextColor(Color.WHITE)
        } else{
            binding.answer1.setBackgroundColor(Color.RED)
            binding.answer1.setTextColor(Color.WHITE)
        }
        if(binding.answer2.text == question.result.toString()){
            binding.answer2.setBackgroundColor(Color.GREEN)
            binding.answer2.setTextColor(Color.WHITE)
        }
        else{
            binding.answer2.setBackgroundColor(Color.RED)
            binding.answer2.setTextColor(Color.WHITE)
        }
        if(binding.answer3.text == question.result.toString()){
            binding.answer3.setBackgroundColor(Color.GREEN)
            binding.answer3.setTextColor(Color.WHITE)
        }
        else{
            binding.answer3.setBackgroundColor(Color.RED)
            binding.answer3.setTextColor(Color.WHITE)
        }
        if(binding.answer4.text == question.result.toString()){
            binding.answer4.setBackgroundColor(Color.GREEN)
            binding.answer4.setTextColor(Color.WHITE)
        }
        else{
            binding.answer4.setBackgroundColor(Color.RED)
            binding.answer4.setTextColor(Color.WHITE)
        }
        if(binding.answer5.text == question.result.toString()){
            binding.answer5.setBackgroundColor(Color.GREEN)
            binding.answer5.setTextColor(Color.WHITE)
        }
        else{
            binding.answer5.setBackgroundColor(Color.RED)
            binding.answer5.setTextColor(Color.WHITE)
        }
    }
    private fun clearColor(){
        binding.answer1.setBackgroundColor(Color.WHITE)
        binding.answer1.setTextColor((Color.parseColor("#6728A7")))
        binding.answer2.setBackgroundColor(Color.WHITE)
        binding.answer2.setTextColor((Color.parseColor("#6728A7")))
        binding.answer3.setBackgroundColor(Color.WHITE)
        binding.answer3.setTextColor((Color.parseColor("#6728A7")))
        binding.answer4.setBackgroundColor(Color.WHITE)
        binding.answer4.setTextColor((Color.parseColor("#6728A7")))
        binding.answer5.setBackgroundColor(Color.WHITE)
        binding.answer5.setTextColor((Color.parseColor("#6728A7")))
    }


}

