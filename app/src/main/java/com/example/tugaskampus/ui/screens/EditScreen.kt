@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tugaskampus.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tugaskampus.model.Task
import com.example.tugaskampus.model.sampleTasks
import com.example.tugaskampus.ui.components.TaskForm
import com.example.tugaskampus.ui.theme.TugasKampusTheme

@Composable
fun EditScreen(task: Task?, onBack: () -> Unit, onSave: (Task) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Tugas") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        if (task == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) { Text("Tugas tidak ditemukan") }
        } else {
            TaskForm(
                initial = task,
                submitLabel = "Simpan perubahan",
                onSubmit = onSave,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditScreenPreview() {
    TugasKampusTheme { EditScreen(task = sampleTasks().first(), onBack = {}, onSave = {}) }
}
