package com.florencenjeri.timefighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    internal lateinit var tapMe: Button
    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    var gameScore = 0
    internal var gameStarted = false
    internal lateinit var countDownTimer: CountDownTimer
    internal val initialCountDownTimer: Long = 60000
    internal val countDownInterval: Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tapMe = findViewById(R.id.tapMeButton)
        gameScoreTextView = findViewById(R.id.gameScoreTextView)
        timeLeftTextView = findViewById(R.id.timeLeftTextView)

        resetGame()

        tapMe.setOnClickListener {
            //Increment the gameScore value
            incrementScore()

        }
    }

    /** Sets up the conditions for a new game */

    private fun resetGame() {
        gameScore = 0

        gameScoreTextView.text = getString(R.string.yourScore, gameScore)

        val initialTimeLeft = initialCountDownTimer / 1000
        timeLeftTextView.text = getString(R.string.timeLeft, initialTimeLeft)

        countDownTimer = object : CountDownTimer(initialCountDownTimer, countDownInterval) {
            override fun onFinish() {
                endGame()
            }

            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                timeLeftTextView.text = getString(R.string.timeLeft, timeLeft)
            }
        }
        gameStarted = false
    }

    private fun incrementScore() {
        if (!gameStarted) {
            startGame()
        }

        gameScore++
        val newScore = getString(R.string.yourScore, gameScore)
        gameScoreTextView.text = newScore
    }

    private fun startGame() {

        countDownTimer.start()
        gameStarted = true

    }

    private fun endGame(){
        Toast.makeText(this,getString(R.string.game_over_message, gameScore),Toast.LENGTH_LONG).show()
        resetGame()
    }


}