package com.florencenjeri.listmaker.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.florencenjeri.listmaker.R

class TaskListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var taskTextView = itemView.findViewById<TextView>(R.id.textview_task)

}