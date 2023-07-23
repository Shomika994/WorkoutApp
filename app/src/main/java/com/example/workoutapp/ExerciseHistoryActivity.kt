package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.ExerciseHistoryBinding
import kotlinx.coroutines.launch

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

        val dao = (application as WorkoutApp).database.historyDao()
        getAllCompletedDates(dao)
    }

    private fun getAllCompletedDates(historyDao: HistoryDao){

        lifecycleScope.launch {
            historyDao.fetchAllDates().collect{

                    if(it.isNotEmpty()){
                        binding?.recycleViewHistory?.visibility = View.VISIBLE
                        binding?.textViewNoHistory?.visibility = View.GONE

                        binding?.recycleViewHistory?.layoutManager = LinearLayoutManager(this@ExerciseHistoryActivity)

                        val dates = ArrayList<String>()

                        for(date in it){
                            dates.add(date.date)
                        }

                        val historyAdapter = HistoryAdapter(dates)
                        binding?.recycleViewHistory?.adapter = historyAdapter

                    } else{
                        binding?.recycleViewHistory?.visibility = View.GONE
                        binding?.textViewNoHistory?.visibility = View.VISIBLE
                    }
                }
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
    }
