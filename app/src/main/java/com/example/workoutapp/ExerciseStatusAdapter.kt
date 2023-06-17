package com.example.workoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ItemExercisePositionBinding

class ExerciseStatusAdapter(private val exercise: ArrayList<ExerciseModel>): RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExercisePositionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.positionView.text = exercise[position].id.toString()
        val model: ExerciseModel = exercise[position]

        when{
            model.isSelected -> {
                holder.positionView.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.selected_exercise)
                holder.positionView.setTextColor(Color.parseColor("#212121"))
            }
            model.isCompleted -> {
                holder.positionView.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.done_exercise)
                holder.positionView.setTextColor(Color.parseColor("#212121"))
            }
            else -> {
                holder.positionView.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.circular_progress_grey)
                holder.positionView.setTextColor(Color.parseColor("#212121"))
            }
        }

    }

    override fun getItemCount(): Int {
        return exercise.size
    }

    inner class ViewHolder(binding: ItemExercisePositionBinding): RecyclerView.ViewHolder(binding.root){
        val positionView = binding.exerciseItem
    }
}