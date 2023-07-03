package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workoutapp.databinding.ExerciseHistoryBinding

class ExerciseHistoryActivity : AppCompatActivity() {

    private var binding: ExerciseHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ExerciseHistoryBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        setSupportActionBar(binding?.historyExerciseToolbar)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.historyExerciseToolbar?.setNavigationOnClickListener{
            val click = onBackPressedDispatcher
            click.onBackPressed()
        }
    }


}