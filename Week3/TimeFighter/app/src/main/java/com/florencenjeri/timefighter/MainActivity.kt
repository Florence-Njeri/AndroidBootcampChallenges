package com.florencenjeri.timefighter

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    internal lateinit var tapMe: Button
    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    var gameScore = 0
    internal var gameStarted = false
    internal lateinit var countDownTimer: CountDownTimer
    internal val initialCountDownTimer: Long = 60000
    internal var timeLeftOnTimer: Long = 60000
    internal val countDownInterval: Long = 1000

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tapMe = findViewById(R.id.tapMeButton)
        gameScoreTextView = findViewById(R.id.gameScoreTextView)
        timeLeftTextView = findViewById(R.id.timeLeftTextView)

        resetGame()

        tapMe.setOnClickListener {
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            it.startAnimation(bounceAnimation)
            //Increment the gameScore value
            incrementScore()

        }
        if (savedInstanceState != null) {
            gameScore = savedInstanceState.getInt(SCORE_KEY)
            timeLeftOnTimer = savedInstanceState.getLong(TIME_LEFT_KEY)
            restoreGame()
        } else {
            resetGame()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionAbout) {
            showInfo()
        }
        return true
    }

    private fun showInfo() {
        TODO("Not yet implemented")
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
                timeLeftOnTimer = millisUntilFinished
                val timeLeft = millisUntilFinished / 1000
                timeLeftTextView.text = getString(R.string.timeLeft, timeLeft)
            }
        }
        gameStarted = false
    }

    private fun restoreGame() {
        gameScoreTextView.text = getString(R.string.yourScore, gameScore)
        val restoredTime = timeLeftOnTimer / 1000

        timeLeftTextView.text =getString(R.string.timeLeft, restoredTime)

        countDownTimer = object : CountDownTimer(timeLeftOnTimer, countDownInterval) {
            override fun onFinish() {
                endGame()
            }

            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
                val timeLeft = millisUntilFinished / 1000
                timeLeftTextView.text = getString(R.string.timeLeft, timeLeft)
            }
        }
        countDownTimer.start()
        gameStarted = true
    }

    private fun incrementScore() {
        if (!gameStarted) {
            startGame()
        }

        gameScore++
        val newScore = getString(R.string.yourScore, gameScore)
        gameScoreTextView.text = newScore
        val blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink)
        gameScoreTextView.startAnimation(blinkAnimation)
    }

    private fun startGame() {

        countDownTimer.start()
        gameStarted = true

    }

    private fun endGame() {
        Toast.makeText(this, getString(R.string.game_over_message, gameScore), Toast.LENGTH_LONG)
            .show()
        resetGame()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(SCORE_KEY, gameScore)
        outState.putLong(TIME_LEFT_KEY, timeLeftOnTimer)
        countDownTimer.cancel()

        Log.d(TAG, "onSaveInstanceState: Saving score: $gameScore & Time Left: $timeLeftOnTimer")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called!!")

    }

}