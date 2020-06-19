package com.florencenjeri.listmaker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.florencenjeri.listmaker.R
import com.florencenjeri.listmaker.data.TaskList

class TaskListAdapter(private val lists: TaskList) :
    RecyclerView.Adapter<TaskListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        //Return a new ViewHolder as required / as per users screen size

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.details_item_view, parent, false)
        return TaskListViewHolder(view)
    }

    override fun getItemCount(): Int {
        //Returns num of item in list
        return lists.tasks.size
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        //Update the ViewData on scroll

        holder.taskTextView.text = lists.tasks[position]


    }

}