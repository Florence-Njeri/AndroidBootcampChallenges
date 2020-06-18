package com.florencenjeri.listmaker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.florencenjeri.listmaker.R
import com.florencenjeri.listmaker.data.TaskList

class ToDoListAdapter(val lists: ArrayList<TaskList>?) :
    RecyclerView.Adapter<TodoListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        //Return a new ViewHolder as required / as per users screen size

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_list_viewholder, parent, false)
        return TodoListViewHolder(view)
    }

    override fun getItemCount(): Int {
        //Returns num of item in list
        return lists?.size!!
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        //Update the ViewData on scroll
        holder.listPositionTextView.text = (position + 1).toString()
        holder.listTitleTextView.text = lists?.get(position)?.name

    }

    fun addList(list: TaskList) {
        lists?.add(list)
        notifyItemInserted(lists!!.size - 1)
    }
}