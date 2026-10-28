@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tugaskampus.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tugaskampus.model.Task
import com.example.tugaskampus.model.TaskOptions
import com.example.tugaskampus.model.sampleTasks
import com.example.tugaskampus.ui.components.ChoiceRow
import com.example.tugaskampus.ui.components.formatDate
import com.example.tugaskampus.ui.theme.TugasKampusTheme

@Composable
fun DetailScreen(
    task: Task?,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onStatusChange: (String) -> Unit
) {
    var showConfirm by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Tugas") },
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    task.mataKuliah,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(task.judul, style = MaterialTheme.typography.headlineSmall)
                Text(
                    task.deskripsi.ifBlank { "Tidak ada deskripsi" },
                    style = MaterialTheme.typography.bodyLarge
                )
                HorizontalDivider()
                InfoRow("Deadline", formatDate(task.deadline))
                InfoRow("Prioritas", task.prioritas)
                ChoiceRow("Status", TaskOptions.statuses, task.status, onStatusChange)
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(onClick = onEdit, modifier = Modifier.weight(1f)) { Text("Edit") }
                    OutlinedButton(
                        onClick = { showConfirm = true },
                        modifier = Modifier.weight(1f)
                    ) { Text("Hapus") }
                }
            }

            if (showConfirm) {
                AlertDialog(
                    onDismissRequest = { showConfirm = false },
                    title = { Text("Hapus tugas?") },
                    text = { Text("Tugas \"${task.judul}\" akan dihapus permanen.") },
                    confirmButton = {
                        TextButton(onClick = {
                            showConfirm = false
                            onDelete()
                        }) { Text("Ya, hapus") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showConfirm = false }) { Text("Batal") }
                    }
                )
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row {
        Text(label, style = MaterialTheme.typography.labelLarge, modifier = Modifier.weight(1f))
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
    TugasKampusTheme {
        DetailScreen(
            task = sampleTasks().first(),
            onBack = {}, onEdit = {}, onDelete = {}, onStatusChange = {}
        )
    }
}
