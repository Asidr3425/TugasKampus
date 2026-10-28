package com.example.tugaskampus.data.repository

import com.example.tugaskampus.data.local.TaskDao
import com.example.tugaskampus.data.local.TaskEntity
import com.example.tugaskampus.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** Menjadi perantara antara ViewModel dan DAO. */
class TaskRepository(private val taskDao: TaskDao) {
    fun observeTasks(): Flow<List<Task>> =
        taskDao.observeAll().map { entities -> entities.map { it.toTask() } }

    suspend fun addTask(task: Task) {
        // Room membuat ID otomatis untuk data baru.
        taskDao.insert(task.toEntity().copy(id = 0))
    }

    suspend fun updateTask(task: Task) {
        taskDao.update(task.toEntity())
    }

    suspend fun updateTaskStatus(taskId: Int, status: String) {
        taskDao.updateStatus(taskId, status)
    }

    suspend fun deleteTask(taskId: Int) {
        taskDao.deleteById(taskId)
    }
}

private fun TaskEntity.toTask() = Task(
    id = id,
    mataKuliah = mataKuliah,
    judul = judul,
    deskripsi = deskripsi,
    deadline = deadline,
    prioritas = prioritas,
    status = status
)

private fun Task.toEntity() = TaskEntity(
    id = id,
    mataKuliah = mataKuliah,
    judul = judul,
    deskripsi = deskripsi,
    deadline = deadline,
    prioritas = prioritas,
    status = status
)
