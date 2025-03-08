package com.example.loukatah.presentation.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AllInbox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.loukatah.data.model.ItemCategory
import com.example.loukatah.presentation.view.navigation.Screen


@Composable
fun BottomNavigationBar(navController: NavController) {
    // Observe the current back stack entry to get the current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = { navController.navigate(Screen.Home.route) },
            icon = {
                Icon(
                    imageVector = Icons.Filled.AllInbox,
                    contentDescription = "Home"
                )
            },
            label = { Text("Home") }
        )


        NavigationBarItem(
            selected = currentRoute == Screen.Account.route,
            onClick = { navController.navigate(Screen.Account.route) },
            icon = {
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "Account"
                )
            },
            label = { Text("Account") }
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Settings.route,
            onClick = { navController.navigate(Screen.Settings.route) },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings"
                )
            },
            label = { Text("Settings") }
        )
    }

}
