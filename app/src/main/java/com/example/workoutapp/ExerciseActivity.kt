package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var binding: ActivityExerciseBinding? = null
    private var countDownTimer: CountDownTimer? = null
    private var totalSeconds = 10
    private var totalSecExercise = 30
    private var progress = 0
    private var countDownExerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolBarExercise)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolBarExercise?.setNavigationOnClickListener{
            val click = onBackPressedDispatcher
            click.onBackPressed()
        }
        setUpTimerView()
    }

    private fun setUpTimerView(){
        if(countDownTimer != null){
            countDownTimer?.cancel()
            progress = 0
        }
        setRestProgressBar()
    }

    private fun setExerciseView(){
        binding?.frameLayout?.visibility = View.INVISIBLE
        binding?.textView?.text = "Exercise"
        binding?.frameLayoutExercise?.visibility = View.VISIBLE

        if(countDownExerciseTimer != null){
            countDownExerciseTimer?.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()
    }

    private fun setRestProgressBar(){

        binding?.progressBar?.progress = progress
        binding?.textViewTimer?.text = totalSeconds.toString()
        val timeTotal = (totalSeconds * 1000) + 1000
        var remainingTime = totalSeconds

        countDownTimer = object : CountDownTimer(timeTotal.toLong(), 1000){
            override fun onTick(millisUntilFinished: Long){
                progress = remainingTime
                binding?.progressBar?.progress = remainingTime * 10
                binding?.textViewTimer?.text = remainingTime.toString()
                remainingTime--
            }
            override fun onFinish(){
                setExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar(){

        binding?.progressBarExercise?.progress = exerciseProgress
        binding?.textViewExercise?.text = totalSecExercise.toString()

        val timeExerciseTotal = (totalSecExercise * 1000) + 1000
        var remainingTimeExercise = totalSecExercise

        countDownExerciseTimer = object : CountDownTimer(timeExerciseTotal.toLong(), 1000){
            override fun onTick(millisUntilFinished: Long){
                exerciseProgress = remainingTimeExercise
                binding?.progressBarExercise?.progress = (remainingTimeExercise * 30) / 10
                binding?.textViewExercise?.text = remainingTimeExercise.toString()
                remainingTimeExercise--
            }
            override fun onFinish(){
                Toast.makeText(applicationContext, "Finished", Toast.LENGTH_SHORT).show()
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