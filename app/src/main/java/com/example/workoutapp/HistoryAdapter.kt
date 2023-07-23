package com.example.workoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.HistoryDatesLayoutBinding

class HistoryAdapter(private val list: ArrayList<String>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(binding: HistoryDatesLayoutBinding): RecyclerView.ViewHolder(binding.root){

        val historyMainLayout = binding.linearLayoutHistoryItemMain
        val position = binding.textViewPosition
        val item = binding.textViewItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(HistoryDatesLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val date: String = list[position]
        holder.position.text = (position + 1).toString()
        holder.item.text = date

        if(position % 2 == 0){
            holder.historyMainLayout.setBackgroundColor(Color.parseColor("#EBEBEB"))
        } else{
            holder.historyMainLayout.setBackgroundColor(Color.parseColor("white"))
        }
    }
}