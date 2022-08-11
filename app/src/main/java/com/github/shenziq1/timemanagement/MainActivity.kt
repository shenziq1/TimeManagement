package com.github.shenziq1.timemanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var scoreTextView: TextView
    private lateinit var timeLeftTextView: TextView
    private lateinit var tapMeButton: Button
    private lateinit var countDownTimer: CountDownTimer
    private var initialCountdown: Long = 2000
    private var countdownInterval: Long = 1000
    private var timeLeft = 60
    private var gameStarted = false
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scoreTextView = findViewById(R.id.your_score_text_view)
        timeLeftTextView = findViewById(R.id.time_left_text_view)
        tapMeButton = findViewById(R.id.tap_me_button)
        tapMeButton.setOnClickListener { incrementScore() }
        resetGame()
    }

    private fun incrementScore(){
        if (!gameStarted){
            startGame()
        }
        score++
        val newScore = getString(R.string.your_score, score)
        scoreTextView.text = newScore
    }

    private fun startGame(){
        countDownTimer.start()
        gameStarted = true
    }


    private fun resetGame(){
        score = 0
        val initialScore = getString(R.string.your_score, score)
        scoreTextView.text = initialScore

        val initialTimeLeft = getString(R.string.time_left, initialCountdown/1000)
        timeLeftTextView.text = initialTimeLeft

        countDownTimer = object : CountDownTimer(initialCountdown, countdownInterval){
            override fun onTick(p0: Long) {
                timeLeft = p0.toInt() / 1000
                val timeLeftString = getString(R.string.time_left, timeLeft)
                timeLeftTextView.text = timeLeftString
            }

            override fun onFinish() {
                endGame()
            }
        }
        gameStarted = false

    }

    private fun endGame(){
        Toast.makeText(this, getString(R.string.game_over_message, score), Toast.LENGTH_LONG).show()
        resetGame()
    }


}