package com.example.loukatah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loukatah.model.Item
import com.example.loukatah.ui.theme.LoukatahTheme
import com.example.loukatah.view.ComingSoon
import com.example.loukatah.view.ItemDetails
import com.example.loukatah.view.MainScreen
import com.example.loukatah.viewmodel.ItemCategoryViewModel
import com.example.loukatah.viewmodel.ItemViewModel
import com.example.loukatah.view.BottomNavigationBar
import com.example.loukatah.view.AddItemButton
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoukatahTheme {
                var selectedScreen = remember { mutableStateOf(0) }
                var selectedItem = remember { mutableStateOf<Item>(Item(
                    id = "",
                    title = "",
                    description = "",
                    status = "",
                    item_category = "",
                    coordinates = Pair(0.0,0.0),
                    location = "",
                    picture = "",
                    date_lost = Date(),
                    createdAt = Date(),
                    updatedAt =Date()
                )) }
                Scaffold(
                    floatingActionButton = {
                        if(selectedScreen.value == 0){
                            AddItemButton()
                        }
                    },
                    bottomBar = {
                        BottomNavigationBar(
                            selectedScreen = selectedScreen.value,
                            onSelected = {
                                selectedScreen.value = it
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    val itemViewModel = viewModel<ItemViewModel>()
                    val itemCategoryViewModel = viewModel<ItemCategoryViewModel>()
                    when(selectedScreen.value){
                        0 -> {
                            MainScreen(
                                itemViewModel,
                                itemCategoryViewModel,
                                modifier = Modifier.padding(innerPadding),
                                onSelectItem = {
                                    selectedItem.value = it
                                    selectedScreen.value = 3
                                }
                            )
                        }
                        3 -> {
                            ItemDetails(selectedItem.value)
                        }
                        else-> {
                            ComingSoon();
                        }
                    }
                }
            }
        }
    }
}