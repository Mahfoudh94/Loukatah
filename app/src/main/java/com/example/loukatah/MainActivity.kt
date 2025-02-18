package com.example.loukatah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loukatah.ui.theme.LoukatahTheme
import com.example.loukatah.view.MainScreen
import com.example.loukatah.viewmodel.ItemCategoryViewModel
import com.example.loukatah.viewmodel.ItemViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoukatahTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val itemViewModel = viewModel<ItemViewModel>()
                    val itemCategoryViewModel = viewModel<ItemCategoryViewModel>()

                    MainScreen(
                        itemViewModel,
                        itemCategoryViewModel,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}