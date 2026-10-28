@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tugaskampus.ui.screens

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tugaskampus.model.Task
import com.example.tugaskampus.ui.components.TaskForm
import com.example.tugaskampus.ui.theme.TugasKampusTheme

@Composable
fun AddScreen(onBack: () -> Unit, onSave: (Task) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Tugas") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        TaskForm(
            initial = null,
            submitLabel = "Simpan",
            onSubmit = onSave,
            modifier = Modifier.padding(padding)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddScreenPreview() {
    TugasKampusTheme { AddScreen(onBack = {}, onSave = {}) }
}
