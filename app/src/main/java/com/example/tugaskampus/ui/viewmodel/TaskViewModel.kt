package com.example.tugaskampus.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugaskampus.data.repository.TaskRepository
import com.example.tugaskampus.model.Task
import com.example.tugaskampus.ui.state.TaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/** Menghubungkan operasi UI ke repository dan mengamati perubahan dari Room. */
class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeTasks()
                .onStart { _uiState.value = _uiState.value.copy(isLoading = true) }
                .catch {
                    // Menutup status loading agar UI tidak macet jika aliran data gagal.
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
                .collect { tasks ->
                    _uiState.value = TaskUiState(tasks = tasks, isLoading = false)
                }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch { repository.addTask(task) }
    }

    fun updateTask(updatedTask: Task) {
        viewModelScope.launch { repository.updateTask(updatedTask) }
    }

    fun updateTaskStatus(taskId: Int, newStatus: String) {
        viewModelScope.launch { repository.updateTaskStatus(taskId, newStatus) }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch { repository.deleteTask(taskId) }
    }
}
