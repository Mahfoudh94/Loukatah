package com.example.loukatah.view

import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavigationBar(
    selectedScreen : Int = 0,
    onSelected : (Int) -> Unit = {},

) {
    BottomNavigation(
        modifier = Modifier.background(color = Color.Transparent)
    ) {
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home",tint = Color(if(selectedScreen == 0) 0xFFFFFFFF else 0xFF6C757D)) },
            selected = selectedScreen == 0,
            onClick = { onSelected(0) },
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Map",tint = Color(if(selectedScreen == 1) 0xFFFFFFFF else 0xFF6C757D)) },
            selected = selectedScreen == 1,
            onClick = { onSelected(1) }
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Profile",tint = Color(if(selectedScreen == 2) 0xFFFFFFFF else 0xFF6C757D)) },
            selected = selectedScreen == 2,
            onClick = { onSelected(2) }
        )
    }
}