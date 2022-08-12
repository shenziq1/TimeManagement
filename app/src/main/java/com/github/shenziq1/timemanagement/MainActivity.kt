package com.github.shenziq1.timemanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var scoreTextView: TextView
    private lateinit var timeLeftTextView: TextView
    private lateinit var tapMeButton: Button
    private lateinit var countDownTimer: CountDownTimer
    private var initialCountdown: Long = 10000
    private var countdownInterval: Long = 1000
    private var gameStarted = false
    private var timeLeft = 10
    private var score = 0

    private val TAG = MainActivity::class.java.simpleName

    companion object{
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreated, Score is: $score")
        scoreTextView = findViewById(R.id.your_score_text_view)
        timeLeftTextView = findViewById(R.id.time_left_text_view)
        tapMeButton = findViewById(R.id.tap_me_button)
        tapMeButton.setOnClickListener { incrementScore() }
        if (savedInstanceState != null){
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeft = savedInstanceState.getInt(TIME_LEFT_KEY)
            restoreGame()
        } else{
            resetGame()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCORE_KEY, score)
        outState.putInt(TIME_LEFT_KEY, timeLeft)
        countDownTimer.cancel()
        Log.d(TAG, "onSaveInstanceState: Saving Score: $score, saving timeLeft: $timeLeft")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "destroy called")
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

    private fun restoreGame(){
        val restoredScore = getString(R.string.your_score, score)
        scoreTextView.text = restoredScore
        val restoredTimeLeft = getString(R.string.time_left, timeLeft)
        timeLeftTextView.text = restoredTimeLeft

        countDownTimer = object : CountDownTimer((timeLeft * 1000).toLong(), countdownInterval){
            override fun onTick(p0: Long) {
                timeLeft = p0.toInt()/1000
                val timeLeftString = getString(R.string.time_left, timeLeft)
                timeLeftTextView.text = timeLeftString
            }

            override fun onFinish() {
                endGame()
            }

        }
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame(){
        Toast.makeText(this, getString(R.string.game_over_message, score), Toast.LENGTH_LONG).show()
        resetGame()
    }


}