package com.example.loukatah.presentation.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.AllInbox
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.loukatah.presentation.view.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    val navigationItems = listOf(
        Triple(Screen.Home, Icons.Filled.AllInbox, Icons.Outlined.AllInbox),
        Triple(Screen.Account, Icons.Filled.AccountBox, Icons.Outlined.AccountBox),
        Triple(Screen.Settings, Icons.Filled.Settings, Icons.Outlined.Settings)
    )

    NavigationBar {
        navigationItems.forEach { (screen, filledIcon, outlinedIcon) ->
            val isSelected = currentRoute == screen.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(screen.route)
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) filledIcon else outlinedIcon,
                        contentDescription = screen.route
                    )
                },
                label = if (isSelected) { { Text(screen.route) } } else null
            )
        }
    }
}
