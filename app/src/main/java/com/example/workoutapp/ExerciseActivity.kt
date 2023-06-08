package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityExerciseBinding
import java.util.ArrayList

class ExerciseActivity : AppCompatActivity() {

    private var binding: ActivityExerciseBinding? = null
    private var countDownTimer: CountDownTimer? = null
    private var totalSeconds = 10
    private var totalSecExercise = 30
    private var progress = 0
    private var countDownExerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolBarExercise)

        exerciseList = Constants.exerciseList()

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
        binding?.frameLayout?.visibility = View.VISIBLE
        binding?.textView?.visibility = View.VISIBLE
        binding?.exerciseTextView?.visibility = View.INVISIBLE
        binding?.frameLayoutExercise?.visibility = View.INVISIBLE
        binding?.imageView?.visibility = View.INVISIBLE
        binding?.nameOfExercise?.text = exerciseList!![currentExercisePosition + 1].getName()

        if(countDownTimer != null){
            countDownTimer?.cancel()
            progress = 0
        }

        setRestProgressBar()
    }

    private fun setExerciseView(){
        binding?.frameLayout?.visibility = View.INVISIBLE
        binding?.textView?.visibility = View.INVISIBLE
        binding?.exerciseTextView?.visibility = View.VISIBLE
        binding?.frameLayoutExercise?.visibility = View.VISIBLE
        binding?.imageView?.visibility = View.VISIBLE
        binding?.upcomingExercise?.visibility = View.INVISIBLE
        binding?.nameOfExercise?.visibility = View.INVISIBLE

        if(countDownExerciseTimer != null){
            countDownExerciseTimer?.cancel()
            exerciseProgress = 0
        }
        binding?.imageView?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.exerciseTextView?.text = exerciseList!![currentExercisePosition].getName()

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
                currentExercisePosition++
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
                binding?.upcomingExercise?.visibility = View.VISIBLE
                binding?.nameOfExercise?.visibility = View.VISIBLE
                binding?.nameOfExercise?.text = exerciseList!![currentExercisePosition].getName()

                if(currentExercisePosition < exerciseList?.size!! - 1){
                    setUpTimerView()
                }else {
                    lastView()
                    Toast.makeText(this@ExerciseActivity, "Congratulations!", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private fun lastView(){
        binding?.imageView?.visibility = View.INVISIBLE
        binding?.upcomingExercise?.visibility = View.INVISIBLE
        binding?.nameOfExercise?.visibility = View.INVISIBLE
        binding?.exerciseTextView?.visibility = View.INVISIBLE
        binding?.frameLayoutExercise?.visibility = View.INVISIBLE
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