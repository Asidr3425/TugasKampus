package com.example.tugaskampus.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/** Bentuk data yang disimpan Room pada tabel tasks. */
@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "mata_kuliah")
    val mataKuliah: String,
    val judul: String,
    val deskripsi: String,
    val deadline: Long,
    val prioritas: String,
    val status: String
)
