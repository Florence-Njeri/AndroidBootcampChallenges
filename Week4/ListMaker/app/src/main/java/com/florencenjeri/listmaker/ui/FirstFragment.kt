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
        todoList = view.findViewById(R.id.todoListRecyclerView)
        todoList.layoutManager= LinearLayoutManager(context)
        todoList.adapter = ToDoListAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      //TODO: To implement
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

    }
}