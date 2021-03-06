/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.raywenderlich.android.foodmart.ui.checkout

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.FlingAnimation
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import com.raywenderlich.android.foodmart.R
import com.raywenderlich.android.foodmart.app.toast
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {
    /**SPRING ANIMATION */
//    private var xPositionDiff = 0f
//    private var yPositionDiff = 0f
//private val springForce: SpringForce by lazy {
//    SpringForce(0f).apply {
//        stiffness = SpringForce.STIFFNESS_MEDIUM
//        dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
//    }
//
//}
//    private val springAnimationX: SpringAnimation by lazy {
//        SpringAnimation(donut, DynamicAnimation.TRANSLATION_X).setSpring(springForce)
//    }
//    private val springAnimationY: SpringAnimation by lazy {
//        SpringAnimation(donut, DynamicAnimation.TRANSLATION_Y).setSpring(springForce)
//    }

    /**FLING ANIMATIONSS*/
    private var donutFlingCount = 0f
    private val donutGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?) = true

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            if (donutFlingCount < 1) {
                donutFlingAnimationX.setStartVelocity(velocityX)
                donutFlingAnimationY.setStartVelocity(velocityY)

                donutFlingAnimationX.start()
                donutFlingAnimationY.start()
            }
            return true
        }
    }

    private val donutGestureDetector: GestureDetector by lazy {
        GestureDetector(this, donutGestureListener)
    }
    private val donutFlingAnimationX: FlingAnimation by lazy {
        FlingAnimation(donut, DynamicAnimation.X).setFriction(1f)
    }
    private val donutFlingAnimationY: FlingAnimation by lazy {
        FlingAnimation(donut, DynamicAnimation.Y).setFriction(1f)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, CheckoutActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        title = getString(R.string.checkout_title)
        //Add a spring animation to the Donut
        setUpAnimatingBlock()
        setupTouchListener()
        setupTreeObserver()
        setupEndListener()
    }

    private fun setUpAnimatingBlock() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        ObjectAnimator.ofFloat(block, "translationX", 0f, width.toFloat() - resources.getDimension(R.dimen.block_width)).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 1000L
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
        }
    }

    /**SPRING ANIMATION LISTENER*/
//    @SuppressLint("ClickableViewAccessibility")
//    private fun setDonutTouchListener() {
//        donut.setOnTouchListener { view, motionEvent ->
//            when (motionEvent?.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    xPositionDiff = motionEvent.rawX - view.x
//                    yPositionDiff = motionEvent.rawY - view.y
//                    springAnimationX.cancel()
//                    springAnimationY.cancel()
//                }
//                MotionEvent.ACTION_MOVE -> {
//                    donut.x = motionEvent.rawX - xPositionDiff
//                    donut.y = motionEvent.rawY - yPositionDiff
//                }
//                MotionEvent.ACTION_UP -> {
//                    springAnimationX.start()
//                    springAnimationY.start()
//                }
//            }
//            true
//        }
//    }

    /** */
    private fun setupTouchListener() {
        donut.setOnTouchListener { _, motionEvent ->
            donutGestureDetector.onTouchEvent(motionEvent)
        }
    }

    private fun setupTreeObserver() {
        donut.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val displayMetrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(displayMetrics)
                val width = displayMetrics.widthPixels
                val height = displayMetrics.heightPixels
                donutFlingAnimationX.setMinValue(0f).setMaxValue((width - donut.width).toFloat())
                donutFlingAnimationY.setMinValue(0f).setMaxValue((height - 2 * donut.height).toFloat())
                donut.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun setupEndListener() {
        donutFlingAnimationX.addEndListener { _, _, _, _ ->
            donutFlingCount += 1
            if (isViewOverlapping(donut, block)) {
                toast(getString(R.string.free_donuts))
            }
        }
    }

    private fun isViewOverlapping(v1: View, v2: View): Boolean {
        val rect1 = Rect()
        v1.getHitRect(rect1)

        val rect2 = Rect()
        v2.getHitRect(rect2)

        return Rect.intersects(rect1, rect2)
    }
}
