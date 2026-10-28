@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tugaskampus.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugaskampus.model.Task
import com.example.tugaskampus.model.TaskOptions
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(millis: Long): String =
    SimpleDateFormat("dd MMM yyyy", Locale.forLanguageTag("id-ID")).format(Date(millis))

@Composable
fun StatusChip(status: String, modifier: Modifier = Modifier) {
    val color = when (status) {
        "Selesai" -> MaterialTheme.colorScheme.tertiaryContainer
        "Proses" -> MaterialTheme.colorScheme.secondaryContainer
        else -> MaterialTheme.colorScheme.errorContainer
    }
    Surface(color = color, shape = MaterialTheme.shapes.small, modifier = modifier) {
        Text(
            text = status,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun TaskCard(task: Task, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(onClick = onClick, modifier = modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = task.mataKuliah,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                StatusChip(task.status)
            }
            Spacer(Modifier.height(4.dp))
            Text(task.judul, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Deadline ${formatDate(task.deadline)} • Prioritas ${task.prioritas}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun ChoiceRow(
    label: String,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(label, style = MaterialTheme.typography.labelLarge)
        Spacer(Modifier.height(4.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            options.forEach { option ->
                FilterChip(
                    selected = option == selected,
                    onClick = { onSelect(option) },
                    label = { Text(option) }
                )
            }
        }
    }
}

/** Form dipakai bersama oleh layar Tambah (initial = null) dan Edit (initial = tugas lama). */
@Composable
fun TaskForm(
    initial: Task?,
    submitLabel: String,
    onSubmit: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    var mataKuliah by rememberSaveable { mutableStateOf(initial?.mataKuliah ?: "") }
    var judul by rememberSaveable { mutableStateOf(initial?.judul ?: "") }
    var deskripsi by rememberSaveable { mutableStateOf(initial?.deskripsi ?: "") }
    var deadline by rememberSaveable { mutableStateOf(initial?.deadline ?: System.currentTimeMillis()) }
    var prioritas by rememberSaveable { mutableStateOf(initial?.prioritas ?: TaskOptions.priorities[1]) }
    var status by rememberSaveable { mutableStateOf(initial?.status ?: TaskOptions.statuses[0]) }
    var showErrors by rememberSaveable { mutableStateOf(false) }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    if (showDatePicker) {
        val pickerState = rememberDatePickerState(initialSelectedDateMillis = deadline)
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    pickerState.selectedDateMillis?.let { deadline = it }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Batal") }
            }
        ) { DatePicker(state = pickerState) }
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = mataKuliah,
            onValueChange = { mataKuliah = it },
            label = { Text("Mata kuliah") },
            singleLine = true,
            isError = showErrors && mataKuliah.isBlank(),
            supportingText = { if (showErrors && mataKuliah.isBlank()) Text("Wajib diisi") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = judul,
            onValueChange = { judul = it },
            label = { Text("Judul tugas") },
            singleLine = true,
            isError = showErrors && judul.isBlank(),
            supportingText = { if (showErrors && judul.isBlank()) Text("Wajib diisi") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = deskripsi,
            onValueChange = { deskripsi = it },
            label = { Text("Deskripsi") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )
        Text("Deadline", style = MaterialTheme.typography.labelLarge)
        OutlinedButton(onClick = { showDatePicker = true }) { Text(formatDate(deadline)) }
        ChoiceRow(
            label = "Prioritas",
            options = TaskOptions.priorities,
            selected = prioritas,
            onSelect = { prioritas = it }
        )
        ChoiceRow(
            label = "Status",
            options = TaskOptions.statuses,
            selected = status,
            onSelect = { status = it }
        )
        Button(
            onClick = {
                if (mataKuliah.isBlank() || judul.isBlank()) {
                    showErrors = true
                } else {
                    onSubmit(
                        Task(
                            id = initial?.id ?: 0,
                            mataKuliah = mataKuliah.trim(),
                            judul = judul.trim(),
                            deskripsi = deskripsi.trim(),
                            deadline = deadline,
                            prioritas = prioritas,
                            status = status
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text(submitLabel) }
    }
}
