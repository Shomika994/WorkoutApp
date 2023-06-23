package com.example.workoutapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workoutapp.databinding.ActivityBmiBinding

class BmiActivity : AppCompatActivity() {

    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityBmiBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        setSupportActionBar(binding?.bmiCalculator)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.buttonCalculator?.setOnClickListener {

            if (validateMetricUnits()) {

                val height = binding?.InputMetricUnitHeight?.text.toString().toFloat() / 100

                val weight = binding?.InputMetricUnitWeight?.text.toString().toFloat()

                val bmi = weight / (height * height)

                calculateBMI(bmi)

            } else {
                Toast.makeText(this, "Invalid inputs", Toast.LENGTH_SHORT).show()
            }

        }

        binding?.bmiCalculator?.setNavigationOnClickListener {
            val click = onBackPressedDispatcher
            click.onBackPressed()
        }
    }

    private fun validateMetricUnits(): Boolean {
        val weightInput = binding?.InputMetricUnitWeight?.text?.toString()?.trim()?.toFloatOrNull()
        val heightInput = binding?.InputMetricUnitHeight?.text?.toString()?.trim()?.toFloatOrNull()

        if(weightInput == null && heightInput == null){
            Toast.makeText(this@BmiActivity, "Please populate height and weight!", Toast.LENGTH_SHORT).show()
            return false
        } else if(weightInput == null){
            Toast.makeText(this@BmiActivity, "Please populate weight", Toast.LENGTH_SHORT).show()
            return false
        } else if(heightInput == null){
            Toast.makeText(this@BmiActivity, "Please populate height", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


    private fun calculateBMI(bmi: Float) {

        var currentState: String = ""
        var advice: String = ""

        if (bmi > 30.0) {
            currentState = "Obese"
            advice = "You need to lose weight! Start with the program right away!"
        } else {
            when (bmi) {
                in 0.0..15.0 -> {
                    currentState = "Severely underweight"
                    advice = "See a doctor immediately!"
                }

                in 16.0..18.5 -> {
                    currentState = "Underweight"
                    advice =
                        "You should really try to intake more proteins and fats.\nCurrent metrics are not healthy."
                }

                in 18.5..25.0 -> {
                    currentState = "Normal"
                    advice = "Your BMI category is optimal! Current metrics are healthy."
                }

                in 25.0..29.9 -> {
                    currentState = "Overweight"
                    advice = "You are overweight! You should exercise a little!"
                }
            }

            binding?.linearLayoutTextViews?.visibility = View.VISIBLE

            val roundBmi = String.format("%.2f", bmi)
            binding?.bmiTextView?.text = roundBmi
            binding?.textViewCurrentState?.text = currentState
            binding?.textViewAdvice?.text = advice
        }
    }
}