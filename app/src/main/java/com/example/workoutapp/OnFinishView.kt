package com.example.workoutapp

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.workoutapp.databinding.ActivityOnFinishViewBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class OnFinishView : AppCompatActivity() {

    private var binding: ActivityOnFinishViewBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnFinishViewBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarFinish)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarFinish?.setNavigationOnClickListener {
            val click = onBackPressedDispatcher
            click.onBackPressed()
        }

        binding?.buttonFinish?.setOnClickListener {
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val dao = (application as WorkoutApp).database.historyDao()
        addUsers(dao)

    }

    private fun addUsers(historyDao: HistoryDao) {

        val calendar = Calendar.getInstance()
        val date = calendar.time
        Log.e("Date : ", "" + date)


        val simpleDateFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.ENGLISH)
        val currentDate = simpleDateFormat.format(date)

        lifecycleScope.launch {
            historyDao.insert(HistoryExerciseEntity(currentDate))
            Log.e("Date : ", "Added...")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}