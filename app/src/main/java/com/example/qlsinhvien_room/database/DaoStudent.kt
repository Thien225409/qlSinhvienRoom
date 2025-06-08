package com.example.qlsinhvien_room.database

import androidx.room.Dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface DaoStudent {
    //them neu mssv chua ton tai
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sv: DataStudent): Long

    @Query("SELECT * FROM students ")
    suspend fun getListStudents(): MutableList<DataStudent>

    @Update
    suspend fun update(sv: DataStudent): Int

    @Query("DELETE FROM students WHERE msSV = :msSV")
    suspend fun delete(msSV: String): Int
}