package com.example.ratelimitedcalculator

class RateLimitedCalculator(var btnClickLimit: Int = 1) {

    fun getSum(num1: Double, num2: Double) : Double {
        return num1 + num2
    }
}