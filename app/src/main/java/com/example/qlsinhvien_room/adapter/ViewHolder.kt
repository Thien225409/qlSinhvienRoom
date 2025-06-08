package com.example.qlsinhvien_room.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qlsinhvien_room.R
import com.example.qlsinhvien_room.adapter.AdapterStudent.OnItemClickListener

class ViewHolder(itemView: View, private val listener: OnItemClickListener) :
    RecyclerView.ViewHolder(itemView) {
    val hoTen: TextView = itemView.findViewById(R.id.hoTenSv)
    val msSV: TextView = itemView.findViewById(R.id.msSv)

    init {
        itemView.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
}