package com.example.tugaskampus.ui.state

import com.example.tugaskampus.model.Task

/** State layar yang dibentuk dari aliran data Room. */
data class TaskUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = true
)
