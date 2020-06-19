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
import com.florencenjeri.listmaker.adapter.TaskListAdapter
import com.florencenjeri.listmaker.data.TaskList
import kotlinx.android.synthetic.main.activity_detail.*

class TaskDetailFragment : Fragment() {
    lateinit var list: TaskList
    lateinit var taskListRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            list = it?.getParcelable(ARG_LIST)!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskListRecyclerView = view.findViewById(R.id.task_list_recyclerview)
        activity.let {
            it?.title = list.name
            taskListRecyclerView.layoutManager = LinearLayoutManager(it)
        }

        taskListRecyclerView.adapter = TaskListAdapter(list)
        add_tasks_button.setOnClickListener {
            setCreateTaskDialog()
        }
    }

    companion object {
        private val ARG_LIST = "list"

        @JvmStatic
        fun newInstance(list: TaskList): TaskDetailFragment {
            val bundle = Bundle()
            bundle.putParcelable(ARG_LIST, list)
            val fragment = TaskDetailFragment()
            fragment.arguments = bundle
            return fragment
        }

    }

    private fun setCreateTaskDialog() {
        activity?.let {
            val taskEditText = EditText(it)
            taskEditText.inputType = InputType.TYPE_CLASS_TEXT
            AlertDialog.Builder(it)
                .setTitle(R.string.task_to_add)
                .setView(taskEditText)
                .setPositiveButton(R.string.add_task) { dialog, _ ->
                    val task = taskEditText.text.toString()
                    list.tasks.add(task)
                    dialog.dismiss()
                }
                .create()
                .show()
        }

    }
}