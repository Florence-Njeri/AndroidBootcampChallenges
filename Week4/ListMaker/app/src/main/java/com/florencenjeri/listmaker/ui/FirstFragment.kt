package com.florencenjeri.listmaker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.florencenjeri.listmaker.R
import com.florencenjeri.listmaker.adapter.ToDoListAdapter
import com.florencenjeri.listmaker.data.ListDataManager

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    lateinit var todoList: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        val dataManager = context?.let { ListDataManager(it) }
        val list = dataManager?.readLists()
        todoList = view.findViewById(R.id.todoListRecyclerView)
        todoList.layoutManager = LinearLayoutManager(context)
        todoList.adapter = ToDoListAdapter(list)
        return view
    }

}