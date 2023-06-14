package com.example.workoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ItemExercisePositionBinding

class ExerciseStatusAdapter(private val exercise: ArrayList<ExerciseModel>): RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExercisePositionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.positionView.text = exercise[position].id.toString()


    }

    override fun getItemCount(): Int {
        return exercise.size
    }

    inner class ViewHolder(binding: ItemExercisePositionBinding): RecyclerView.ViewHolder(binding.root){
        val positionView = binding.exerciseItem
    }
}