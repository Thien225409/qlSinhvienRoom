package com.example.qlsinhvien_room.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class DataStudent(
    val hoTenSv: String,
    @PrimaryKey
    val msSV: String,
    val ngaySinh: String,
    val eMail: String,
    val address: String
) {
}