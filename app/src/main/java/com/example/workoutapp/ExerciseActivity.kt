package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var binding: ActivityExerciseBinding? = null
    private var countDownTimer: CountDownTimer? = null
    private var totalSeconds: Long = 10000
    private var progress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val timerText = findViewById<TextView>(R.id.text_view_timer)
        timerText.text = "${totalSeconds / 1000}"

        setUpTimerView()
        setSupportActionBar(binding?.toolBarExercise)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolBarExercise?.setNavigationOnClickListener{
           val click = onBackPressedDispatcher
            click.onBackPressed()
        }

    }

    private fun setUpTimerView(){
        if(countDownTimer != null){
            countDownTimer?.cancel()
            progress = 0
        }
        setRestProgressBar()
    }

    private fun setRestProgressBar(){

        binding?.progressBar?.progress = progress

        countDownTimer = object : CountDownTimer(11000, 1000){
            override fun onTick(millisUntilFinished: Long){
                progress = (totalSeconds - millisUntilFinished).toInt()
                binding?.textViewTimer?.text = (millisUntilFinished / 1000).toString()

                val progressPercentage = (millisUntilFinished.toFloat() / totalSeconds * 100).toInt()

                binding?.progressBar?.progress = progressPercentage

            }
            override fun onFinish(){
                Toast.makeText(applicationContext, "Exercise is starting!", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    public override fun onDestroy(){
        if(countDownTimer != null){
            countDownTimer?.cancel()
            progress = 0
        }
        super.onDestroy()
        binding = null
    }


}