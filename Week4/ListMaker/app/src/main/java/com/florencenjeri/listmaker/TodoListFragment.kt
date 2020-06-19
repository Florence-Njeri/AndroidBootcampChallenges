package com.florencenjeri.listmaker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.florencenjeri.listmaker.adapter.ToDoListAdapter
import com.florencenjeri.listmaker.data.ListDataManager
import com.florencenjeri.listmaker.data.TaskList

// TODO: Rename parameter arguments, choose names that match


/**
 * A simple [Fragment] subclass.
 * Use the [TodoListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodoListFragment : Fragment(), ToDoListAdapter.TodoListClickListener {
    private var listener: OnFragmentInteractionListener? = null
    lateinit var todoList: RecyclerView
    lateinit var dataManager: ListDataManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
            dataManager = ListDataManager(context)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lists = dataManager.readLists()

        todoList = view.findViewById(R.id.todoListRecyclerView)
        todoList.layoutManager = LinearLayoutManager(activity)
        todoList.adapter = ToDoListAdapter(lists, this)
    }

    interface OnFragmentInteractionListener {
        fun onTodoListClicked(list: TaskList)
    }

    companion object {

        fun newInstance(): TodoListFragment {
            return TodoListFragment()
        }

    }

    override fun listItemClicked(list: TaskList) {
        listener?.onTodoListClicked(list)
    }

    fun addList(list: TaskList) {
        dataManager.saveList(list)
        val todoAdapter = todoList.adapter as ToDoListAdapter
        todoAdapter.addList(list)

    }

    fun saveList(list: TaskList) {
        dataManager.saveList(list)
        updateList()
    }

    private fun updateList() {
        val lists = dataManager.readLists()
        todoList.adapter = ToDoListAdapter(lists, this)
    }

}