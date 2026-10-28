package com.example.tugaskampus.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tugaskampus.model.Task
import com.example.tugaskampus.model.sampleTasks
import com.example.tugaskampus.ui.state.TaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Mengelola data dan aksi tugas untuk UI.
 * Sumber data sementara masih berupa StateFlow di memori; Room dipasang pada Langkah 4.
 */
class TaskViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TaskUiState(tasks = sampleTasks()))
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    private var nextId: Int = (_uiState.value.tasks.maxOfOrNull { it.id } ?: 0) + 1

    fun addTask(task: Task) {
        val taskWithId = task.copy(id = nextId)
        nextId += 1
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks + taskWithId
        )
    }

    fun updateTask(updatedTask: Task) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.map { task ->
                if (task.id == updatedTask.id) updatedTask else task
            }
        )
    }

    fun updateTaskStatus(taskId: Int, newStatus: String) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.map { task ->
                if (task.id == taskId) task.copy(status = newStatus) else task
            }
        )
    }

    fun deleteTask(taskId: Int) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.filterNot { it.id == taskId }
        )
    }
}
