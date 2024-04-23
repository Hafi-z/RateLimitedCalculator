package com.example.ratelimitedcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    val clickTimeLimitInMillisec: Long = 5000 //taking 5 sec for simplicity
    private var lastClickedTime: Long = 0
    private var btnClickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calculator = RateLimitedCalculator(1)
        val number1 : Double = 1.0 //getUserInput()=black box
        val number2 : Double = 2.0

        val addButton: Button = findViewById(R.id.button_add)
        addButton.setOnClickListener {
            if (SystemClock.elapsedRealtime() - lastClickedTime > clickTimeLimitInMillisec) {
                btnClickCount = 0
            }
            lastClickedTime = SystemClock.elapsedRealtime()
            btnClickCount+=1
            if (isClickEnabled(btnClickCount, calculator.btnClickLimit)) {
                val result = calculator.getSum(number1, number2)
                Log.d("MainActivity", "Result: $result")
                //showResultOnScreen() //black box
            } else {
                showToast("Try again after a short delay")
            }
        }
    }

    private fun isClickEnabled(btnClickCount: Int, btnClickLimit: Int): Boolean {
        if (SystemClock.elapsedRealtime() - lastClickedTime <= clickTimeLimitInMillisec) {
            return btnClickCount <= btnClickLimit
        }
        lastClickedTime = SystemClock.elapsedRealtime()
        this.btnClickCount = 0
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}