package com.florencenjeri.listmaker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.florencenjeri.listmaker.R
import com.florencenjeri.listmaker.data.ToDo

class ToDoListAdapter : RecyclerView.Adapter<TodoListViewHolder>() {
    var todoList = arrayOf("Android Development", "House Work", "Errands", "Shopping")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        //Return a new ViewHolder as required / as per users screen size

        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_list_viewholder,parent,false)
        return TodoListViewHolder(view)
    }

    override fun getItemCount(): Int {
        //Returns num of item in list
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        //Update the ViewData on scroll
        holder.listPositionTextView.text= (position + 1).toString()
        holder.listTitleTextView.text= todoList[position]

    }
}