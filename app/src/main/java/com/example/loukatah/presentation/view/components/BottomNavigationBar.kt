package com.example.loukatah.presentation.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem








import androidx.compose.runtime.getValue

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.loukatah.presentation.view.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = { navController.navigate(Screen.Home.route) },
            icon = { Icon(Icons.Filled.AllInbox, contentDescription = "الرئيسية") },
            label = { Text("الرئيسية") }
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Account.route,
            onClick = { navController.navigate(Screen.Account.route) },
            icon = { Icon(Icons.Filled.AccountBox, contentDescription = "الحساب") },
            label = { Text("الحساب") }
        )

        NavigationBarItem(
            selected = currentRoute == "add_item",
            onClick = { navController.navigate("add_item") },
            icon = { Icon(Icons.Filled.Add, contentDescription = "إضافة عنصر") },
            label = { Text("إضافة") }
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Settings.route,
            onClick = { navController.navigate(Screen.Settings.route) },
            icon = { Icon(Icons.Filled.Settings, contentDescription = "الإعدادات") },
            label = { Text("الإعدادات") }
        )
    }
}
