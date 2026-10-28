package com.example.tugaskampus.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tugaskampus.ui.screens.AddScreen
import com.example.tugaskampus.ui.screens.DetailScreen
import com.example.tugaskampus.ui.screens.EditScreen
import com.example.tugaskampus.ui.screens.ListScreen
import com.example.tugaskampus.ui.viewmodel.TaskViewModel

private object TaskRoutes {
    const val LIST = "list"
    const val ADD = "add"
    const val DETAIL = "detail/{taskId}"
    const val EDIT = "edit/{taskId}"

    fun detail(taskId: Int) = "detail/$taskId"
    fun edit(taskId: Int) = "edit/$taskId"
}

@Composable
fun AppNavGraph(taskViewModel: TaskViewModel = viewModel()) {
    val navController = rememberNavController()
    val uiState by taskViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = TaskRoutes.LIST
    ) {
        composable(TaskRoutes.LIST) {
            ListScreen(
                tasks = uiState.tasks,
                onAddClick = { navController.navigate(TaskRoutes.ADD) },
                onTaskClick = { taskId ->
                    navController.navigate(TaskRoutes.detail(taskId))
                }
            )
        }

        composable(TaskRoutes.ADD) {
            AddScreen(
                onBack = { navController.popBackStack() },
                onSave = { newTask ->
                    taskViewModel.addTask(newTask)
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = TaskRoutes.DETAIL,
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: -1
            val task = uiState.tasks.find { it.id == taskId }

            DetailScreen(
                task = task,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate(TaskRoutes.edit(taskId)) },
                onDelete = {
                    taskViewModel.deleteTask(taskId)
                    navController.popBackStack(TaskRoutes.LIST, inclusive = false)
                },
                onStatusChange = { newStatus ->
                    taskViewModel.updateTaskStatus(taskId, newStatus)
                }
            )
        }

        composable(
            route = TaskRoutes.EDIT,
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: -1
            val task = uiState.tasks.find { it.id == taskId }

            EditScreen(
                task = task,
                onBack = { navController.popBackStack() },
                onSave = { updatedTask ->
                    taskViewModel.updateTask(updatedTask)
                    navController.popBackStack(TaskRoutes.LIST, inclusive = false)
                }
            )
        }
    }
}
