package com.example.loukatah.view
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddItemButton(modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = {},
        modifier = Modifier.background(Color.Blue, shape = RoundedCornerShape(12.dp))
        // backgroundColor = Color.Blue
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Item", tint = Color.White)
    }
}