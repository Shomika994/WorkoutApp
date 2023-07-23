package com.example.workoutapp

import android.app.Application


class WorkoutApp: Application() {

    val database: HistoryDatabase by lazy {
        HistoryDatabase.getInstance(this)
    }
}