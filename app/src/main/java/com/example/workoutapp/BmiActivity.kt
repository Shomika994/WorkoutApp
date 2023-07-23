package com.example.workoutapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        metricUnitsShow()
        updateButtonVisibility()

        binding?.radioGroup?.setOnCheckedChangeListener { _, check: Int ->

            if (check == R.id.button_metric) {
                metricUnitsShow()
            } else {
                imperialUnitsShow()
            }
            binding?.buttonCalculator?.visibility = View.VISIBLE
        }

        binding?.buttonCalculator?.setOnClickListener {
            calculateUnits(binding?.radioGroup?.checkedRadioButtonId)
        }

        binding?.bmiCalculator?.setNavigationOnClickListener {
            val click = onBackPressedDispatcher
            click.onBackPressed()
        }
    }

    private fun updateButtonVisibility() {
        binding?.InputMetricUnitHeight?.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding?.MetricUnitHeight?.visibility = View.VISIBLE
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding?.buttonCalculator?.visibility = View.VISIBLE
                binding?.linearLayoutTextViews?.visibility = View.INVISIBLE
            }
            override fun afterTextChanged(s: Editable?) {
                binding?.buttonCalculator?.visibility = View.VISIBLE
            }
        })
        binding?.InputMetricUnitWeight?.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding?.MetricUNitWeight?.visibility = View.VISIBLE
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding?.buttonCalculator?.visibility = View.VISIBLE
                binding?.linearLayoutTextViews?.visibility = View.INVISIBLE
            }
            override fun afterTextChanged(s: Editable?) {
                binding?.buttonCalculator?.visibility = View.VISIBLE
            }
        })
        binding?.InputImperialUnitFeet?.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding?.ImperialUnitHeightFeet?.visibility = View.VISIBLE
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding?.buttonCalculator?.visibility = View.VISIBLE
                binding?.linearLayoutTextViews?.visibility = View.INVISIBLE
            }
            override fun afterTextChanged(s: Editable?) {
                binding?.buttonCalculator?.visibility = View.VISIBLE
            }
        })
        binding?.InputImperialUnitInch?.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding?.ImperialUnitHeightInch?.visibility = View.VISIBLE
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding?.buttonCalculator?.visibility = View.VISIBLE
                binding?.linearLayoutTextViews?.visibility = View.INVISIBLE
            }
            override fun afterTextChanged(s: Editable?) {
                binding?.buttonCalculator?.visibility = View.VISIBLE
            }
        })
        binding?.InputImperialUnitWeight?.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding?.ImperialUnitWeight?.visibility = View.VISIBLE
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding?.buttonCalculator?.visibility = View.VISIBLE
                binding?.linearLayoutTextViews?.visibility = View.INVISIBLE
            }
            override fun afterTextChanged(s: Editable?) {
                binding?.buttonCalculator?.visibility = View.VISIBLE
            }
        })
    }

    private fun calculateUnits(checkedRadioButtonId: Int?) {

        when (checkedRadioButtonId) {
            R.id.button_metric -> {
                if (validateMetricUnits()) {

                    val height = binding?.InputMetricUnitHeight?.text.toString().toFloat() / 100

                    val weight = binding?.InputMetricUnitWeight?.text.toString().toFloat()

                    val bmi = weight / (height * height)

                    calculateBMI(bmi)
                }
            }
            R.id.button_imperial -> {
                if (validateImperialUnits()) {
                    val feet: String = binding?.InputImperialUnitFeet?.text.toString()
                    val inch: String = binding?.InputImperialUnitInch?.text.toString()
                    val weight: Float = binding?.InputImperialUnitWeight?.text.toString().toFloat()

                    if(inch.isEmpty()){
                        Toast.makeText(this, "Please populate inches!", Toast.LENGTH_SHORT).show()
                    } else {
                        val totalHeight = inch.toFloat() + feet.toFloat() * 12
                        val bmi = (weight / (totalHeight * totalHeight)) * 703
                        calculateBMI(bmi)
                    }
                }
            }
        }
    }


    private fun validateImperialUnits(): Boolean {

        val weight = binding?.InputImperialUnitWeight?.text?.toString()?.trim()?.toFloatOrNull()
        val feet = binding?.InputImperialUnitFeet?.text?.toString()?.trim()?.toFloatOrNull()
        val inch = binding?.InputImperialUnitInch?.text?.toString()?.trim()?.toFloatOrNull()

        when {

            inch == null && feet == null && weight == null -> {
                Toast.makeText(this, "Please populate feet, inches and weight!", Toast.LENGTH_SHORT).show()
                return false
            }

            weight == null && inch == null -> {
                Toast.makeText(this, "Please populate weight and inches!", Toast.LENGTH_SHORT).show()
                return false
            }

            feet == null && inch == null-> {
                Toast.makeText(this, "Please populate feet and inches!", Toast.LENGTH_SHORT).show()
                return false
            }

            inch != null && feet == null && weight == null -> {
                Toast.makeText(this, "Please populate feet and weight!", Toast.LENGTH_SHORT).show()
                return false
            }

            weight == null ->{
                Toast.makeText(this, "Please populate weight!", Toast.LENGTH_SHORT).show()
                return false
            }
            feet == null -> {
                Toast.makeText(this, "Please populate feet!", Toast.LENGTH_SHORT).show()
                return false
            }

            inch == null -> {
                Toast.makeText(this, "Please populate inches!", Toast.LENGTH_SHORT).show()
                return false
            }

        }
        return true
    }

    private fun metricUnitsShow() {

        binding?.MetricUnitHeight?.visibility = View.VISIBLE
        binding?.MetricUNitWeight?.visibility = View.VISIBLE
        binding?.imperialUnitLayout?.visibility = View.GONE
        binding?.ImperialUnitWeight?.visibility = View.GONE

        binding?.InputMetricUnitWeight?.text?.clear()
        binding?.InputMetricUnitHeight?.text?.clear()

        binding?.linearLayoutTextViews?.visibility = View.INVISIBLE

    }

    private fun imperialUnitsShow() {

        binding?.MetricUnitHeight?.visibility = View.GONE
        binding?.MetricUNitWeight?.visibility = View.GONE
        binding?.imperialUnitLayout?.visibility = View.VISIBLE
        binding?.ImperialUnitWeight?.visibility = View.VISIBLE

        binding?.InputImperialUnitFeet?.text?.clear()
        binding?.InputImperialUnitInch?.text?.clear()
        binding?.InputImperialUnitWeight?.text?.clear()

        binding?.linearLayoutTextViews?.visibility = View.INVISIBLE

    }

    private fun validateMetricUnits(): Boolean {

        val weightInput = binding?.InputMetricUnitWeight?.text?.toString()?.trim()?.toFloatOrNull()
        val heightInput = binding?.InputMetricUnitHeight?.text?.toString()?.trim()?.toFloatOrNull()

        if (weightInput == null && heightInput == null) {
            Toast.makeText(
                this@BmiActivity,
                "Please populate height and weight!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (weightInput == null) {
            Toast.makeText(this@BmiActivity, "Please populate weight", Toast.LENGTH_SHORT).show()
            return false
        } else if (heightInput == null) {
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
            binding?.buttonCalculator?.visibility = View.INVISIBLE

            val roundBmi = String.format("%.2f", bmi)
            binding?.bmiTextView?.text = roundBmi
            binding?.textViewCurrentState?.text = currentState
            binding?.textViewAdvice?.text = advice
        }
    }
}

