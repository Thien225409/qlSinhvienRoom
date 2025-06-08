package com.example.qlsinhvien_room.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qlsinhvien_room.R
import com.example.qlsinhvien_room.database.DataStudent

class AdapterStudent(
    private val listStudents: MutableList<DataStudent>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemsv, parent, false)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val sv = listStudents[position]
        holder.hoTen.text = sv.hoTenSv.toString()
        holder.msSV.text = sv.msSV.toString()
    }

    override fun getItemCount() = listStudents.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}