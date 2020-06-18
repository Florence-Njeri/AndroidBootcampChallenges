package com.florencenjeri.listmaker.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.florencenjeri.listmaker.R

class TodoListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var listPositionTextView =itemView.findViewById<TextView>(R.id.item_number)
    var listTitleTextView =itemView.findViewById<TextView>(R.id.itemString)
}