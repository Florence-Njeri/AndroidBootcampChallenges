package com.florencenjeri.listmaker

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.florencenjeri.listmaker.adapter.ToDoListAdapter
import com.florencenjeri.listmaker.data.ListDataManager
import com.florencenjeri.listmaker.data.TaskList
import kotlinx.android.synthetic.main.fragment_todo_list.*

// TODO: Rename parameter arguments, choose names that match


/**
 * A simple [Fragment] subclass.
 * Use the [TodoListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodoListFragment : Fragment(), ToDoListAdapter.TodoListClickListener {

    lateinit var todoList: RecyclerView
    lateinit var dataManager: ListDataManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            dataManager = ListDataManager(it)
        }
        val lists = dataManager.readLists()

        todoList = view.findViewById(R.id.todoListRecyclerView)
        todoList.layoutManager = LinearLayoutManager(activity)
        todoList.adapter = ToDoListAdapter(lists, this)
        fab.setOnClickListener { _ ->
            showCreateTodoListDialog()
        }

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
    }

    private fun showCreateTodoListDialog() {
        activity.let {
            val dialogTitle = getString(R.string.name_of_list)
            val positiveButtonTitle = getString(R.string.create_list)
            val myDialog = AlertDialog.Builder(it!!)

            val todoTitleEditText = EditText(it)
            todoTitleEditText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

            myDialog.setTitle(dialogTitle)
            myDialog.setView(todoTitleEditText)

            myDialog.setPositiveButton(positiveButtonTitle) { dialog, _ ->

                val list = TaskList(todoTitleEditText.text.toString())
                addList(list)
                dialog.dismiss()
                showTaskListItems(list)
            }

            myDialog.create().show()
        }


    }

    private fun showTaskListItems(list: TaskList) {

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