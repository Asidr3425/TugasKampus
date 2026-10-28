package com.example.tugaskampus.ui.state

import com.example.tugaskampus.model.Task

/** State yang dibaca layar. Pada Langkah 3, data masih disimpan di memori. */
data class TaskUiState(
    val tasks: List<Task> = emptyList()
)
