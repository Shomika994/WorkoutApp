package com.example.workoutapp

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "history-table")
data class HistoryExerciseEntity(
    @PrimaryKey
    val date: String)
