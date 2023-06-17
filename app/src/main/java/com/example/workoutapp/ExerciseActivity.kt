package com.example.workoutapp

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.ActivityExerciseBinding
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding: ActivityExerciseBinding? = null
    private var countDownTimer: CountDownTimer? = null
    private var totalSeconds = 10
    private var totalSecExercise = 30
    private var progress = 0
    private var countDownExerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1
    private var textToSpeech: TextToSpeech? = null
    private var player: MediaPlayer? = null
    private var adapter: ExerciseStatusAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolBarExercise)

        exerciseList = Constants.exerciseList()
        textToSpeech = TextToSpeech(this, this)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolBarExercise?.setNavigationOnClickListener {
            val click = onBackPressedDispatcher
            click.onBackPressed()
        }
        setUpTimerView()
        setUpExerciseList()
    }

    private fun setUpExerciseList() {
        binding?.recycleViewExercise?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.recycleViewExercise?.adapter = adapter
    }

    private fun setUpTimerView() {

        try {
            val soundURI =
                Uri.parse("android.resource://com.example.workoutapp/" + R.raw.app_src_main_res_raw_press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding?.frameLayout?.visibility = View.VISIBLE
        binding?.textView?.visibility = View.VISIBLE
        binding?.exerciseTextView?.visibility = View.INVISIBLE
        binding?.frameLayoutExercise?.visibility = View.INVISIBLE
        binding?.imageView?.visibility = View.INVISIBLE
        binding?.nameOfExercise?.text = exerciseList!![currentExercisePosition + 1].name

        if (countDownTimer != null) {
            countDownTimer?.cancel()
            progress = 0
        }

        setRestProgressBar()
    }

    private fun setExerciseView() {
        binding?.frameLayout?.visibility = View.INVISIBLE
        binding?.textView?.visibility = View.INVISIBLE
        binding?.exerciseTextView?.visibility = View.VISIBLE
        binding?.frameLayoutExercise?.visibility = View.VISIBLE
        binding?.imageView?.visibility = View.VISIBLE
        binding?.upcomingExercise?.visibility = View.INVISIBLE
        binding?.nameOfExercise?.visibility = View.INVISIBLE

        if (countDownExerciseTimer != null) {
            countDownExerciseTimer?.cancel()
            exerciseProgress = 0
        }
        binding?.imageView?.setImageResource(exerciseList!![currentExercisePosition].image)
        binding?.exerciseTextView?.text = exerciseList!![currentExercisePosition].name

        speakOut(exerciseList!![currentExercisePosition].name)
        setExerciseProgressBar()
    }

    private fun setRestProgressBar() {

        binding?.progressBar?.progress = progress
        binding?.textViewTimer?.text = totalSeconds.toString()
        val timeTotal = (totalSeconds * 1000) + 1000
        var remainingTime = totalSeconds

        countDownTimer = object : CountDownTimer(timeTotal.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                progress = remainingTime
                binding?.progressBar?.progress = remainingTime * 10
                binding?.textViewTimer?.text = remainingTime.toString()
                remainingTime--
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].isSelected = true
                adapter!!.notifyDataSetChanged()
                setExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar() {

        binding?.progressBarExercise?.progress = exerciseProgress
        binding?.textViewExercise?.text = totalSecExercise.toString()

        val timeExerciseTotal = (totalSecExercise * 1000) + 1000
        var remainingTimeExercise = totalSecExercise

        countDownExerciseTimer = object : CountDownTimer(timeExerciseTotal.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress = remainingTimeExercise
                binding?.progressBarExercise?.progress = (remainingTimeExercise * 30) / 10
                binding?.textViewExercise?.text = remainingTimeExercise.toString()
                remainingTimeExercise--
            }

            override fun onFinish() {

                binding?.upcomingExercise?.visibility = View.VISIBLE
                binding?.nameOfExercise?.visibility = View.VISIBLE
                binding?.nameOfExercise?.text = exerciseList!![currentExercisePosition].name
                speakOut("REST!")

                if (currentExercisePosition < exerciseList?.size!! - 1) {
                    exerciseList!![currentExercisePosition].isSelected = false
                    adapter!!.notifyDataSetChanged()
                    exerciseList!![currentExercisePosition].isCompleted = true
                    adapter!!.notifyDataSetChanged()
                    setUpTimerView()
                } else {
                    finish()
                    lastView()
                    speakOut("DONE")
                    val intent = Intent(this@ExerciseActivity, OnFinishView::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    private fun lastView() {
        binding?.imageView?.visibility = View.INVISIBLE
        binding?.upcomingExercise?.visibility = View.INVISIBLE
        binding?.nameOfExercise?.visibility = View.INVISIBLE
        binding?.exerciseTextView?.visibility = View.INVISIBLE
        binding?.frameLayoutExercise?.visibility = View.INVISIBLE
    }

    public override fun onDestroy() {
        if (countDownTimer != null) {
            countDownTimer?.cancel()
            progress = 0
        }

        if (countDownExerciseTimer != null) {
            countDownExerciseTimer?.cancel()
            exerciseProgress = 0
        }

        if (textToSpeech != null) {
            textToSpeech?.stop()
            textToSpeech?.shutdown()
        }

        if (player != null) {
            player?.stop()
        }

        super.onDestroy()
        binding = null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale.ENGLISH)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("textToSpeech", "Please enter text")
            } else {
                Log.e("textToSpeech", "Initialization failed")
            }
        }
    }

    private fun speakOut(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

}