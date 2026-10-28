package com.example.tugaskampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.tugaskampus.model.Task
import com.example.tugaskampus.model.sampleTasks
import com.example.tugaskampus.ui.screens.AddScreen
import com.example.tugaskampus.ui.screens.DetailScreen
import com.example.tugaskampus.ui.screens.EditScreen
import com.example.tugaskampus.ui.screens.ListScreen
import com.example.tugaskampus.ui.theme.TugasKampusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TugasKampusTheme { DemoHost() }
        }
    }
}

private enum class Screen { LIST, ADD, DETAIL, EDIT }

/**
 * SEMENTARA (Langkah 2): penghubung layar sederhana dengan data di memori,
 * hanya agar keempat layar bisa dicoba dan di-screenshot.
 * Diganti Navigation + ViewModel di Langkah 3, dan Room di Langkah 4.
 */
@Composable
private fun DemoHost() {
    val tasks = remember { mutableStateListOf<Task>().apply { addAll(sampleTasks()) } }
    var screen by remember { mutableStateOf(Screen.LIST) }
    var selectedId by remember { mutableIntStateOf(0) }
    var nextId by remember { mutableIntStateOf(100) }
    val selected = tasks.find { it.id == selectedId }

    BackHandler(enabled = screen != Screen.LIST) { screen = Screen.LIST }

    when (screen) {
        Screen.LIST -> ListScreen(
            tasks = tasks,
            onAddClick = { screen = Screen.ADD },
            onTaskClick = { id -> selectedId = id; screen = Screen.DETAIL }
        )
        Screen.ADD -> AddScreen(
            onBack = { screen = Screen.LIST },
            onSave = { newTask ->
                tasks.add(newTask.copy(id = nextId++))
                screen = Screen.LIST
            }
        )
        Screen.DETAIL -> DetailScreen(
            task = selected,
            onBack = { screen = Screen.LIST },
            onEdit = { screen = Screen.EDIT },
            onDelete = {
                tasks.removeAll { it.id == selectedId }
                screen = Screen.LIST
            },
            onStatusChange = { newStatus ->
                val i = tasks.indexOfFirst { it.id == selectedId }
                if (i >= 0) tasks[i] = tasks[i].copy(status = newStatus)
            }
        )
        Screen.EDIT -> EditScreen(
            task = selected,
            onBack = { screen = Screen.DETAIL },
            onSave = { updated ->
                val i = tasks.indexOfFirst { it.id == updated.id }
                if (i >= 0) tasks[i] = updated
                screen = Screen.LIST
            }
        )
    }
}
