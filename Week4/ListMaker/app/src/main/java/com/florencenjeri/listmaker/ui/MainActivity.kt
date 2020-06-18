package com.florencenjeri.listmaker.ui

import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.florencenjeri.listmaker.R
import com.florencenjeri.listmaker.adapter.ToDoListAdapter
import com.florencenjeri.listmaker.data.ListDataManager
import com.florencenjeri.listmaker.data.TaskList
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var dataManager: ListDataManager
    lateinit var lists: ArrayList<TaskList>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        dataManager = ListDataManager(this)
        lists = dataManager.readLists()
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { _ ->
            showCreateTodoListDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showCreateTodoListDialog() {
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)
        val negativeButtonTitle = getString(R.string.cancel_list_creation)
        val myDialog = AlertDialog.Builder(this)

        val todoTitleEditText = EditText(this)
        todoTitleEditText.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

        myDialog.setTitle(dialogTitle)
        myDialog.setView(todoTitleEditText)

        myDialog.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            val adapter = ToDoListAdapter(lists)
            val list = TaskList(todoTitleEditText.text.toString())
            dataManager.saveList(list)
            adapter.addList(list)
            dialog.dismiss()
        }

        myDialog.create().show()

    }
}