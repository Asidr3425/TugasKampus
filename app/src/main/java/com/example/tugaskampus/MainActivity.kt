package com.example.tugaskampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tugaskampus.navigation.AppNavGraph
import com.example.tugaskampus.ui.theme.TugasKampusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TugasKampusTheme {
                AppNavGraph()
            }
        }
    }
}
