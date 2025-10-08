package com.example.nba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.nba.di.ServiceLocator
import com.example.nba.ui.AppRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ServiceLocator.init(applicationContext)
        enableEdgeToEdge()
        setContent { AppRoot() }
    }
}