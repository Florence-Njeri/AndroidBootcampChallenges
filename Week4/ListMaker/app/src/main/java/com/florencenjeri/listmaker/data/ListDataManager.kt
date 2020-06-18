package com.florencenjeri.listmaker.data

import android.content.Context
import androidx.preference.PreferenceManager

class ListDataManager(private val context: Context) {
    //Save data
    fun saveList(list: TaskList) {

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPrefs.putStringSet(list.name, list.tasks.toHashSet())
        sharedPrefs.apply()
    }

    fun readLists(): ArrayList<TaskList> {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val contents = sharedPrefs.all
        val taskLists = ArrayList<TaskList>()

        for (taskList in contents) {
            val taskItem = ArrayList(taskList.value as HashSet<String>)
            val list = TaskList(taskList.key, taskItem)
            taskLists.add(list)
        }
        return taskLists
    }
}