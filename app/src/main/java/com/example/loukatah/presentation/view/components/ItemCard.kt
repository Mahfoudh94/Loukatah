package com.example.loukatah.presentation.view.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.loukatah.data.model.Item
import java.text.SimpleDateFormat

/**
 * Card component for displaying an item
 * 
 * @param item The item to display
 * @param onItemClick Callback when the item is clicked
 * @param modifier Modifier for customizing the layout
 */
@SuppressLint("SimpleDateFormat")
@Composable
fun ItemCard(
    item: Item,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .clickable { onItemClick(item.id) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.picture,
                contentDescription = item.title,
                modifier = Modifier
                    .width(80.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.padding(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = item.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    StatusTag(status = item.status)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${item.status} Date: ${SimpleDateFormat("MMM dd, yyyy").format(item.date_lost)}",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

/**
 * Status tag component for displaying item status (Lost/Found)
 * 
 * @param status The status to display
 * @param modifier Modifier for customizing the layout
 */
@Composable
fun StatusTag(
    status: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(
                when (status) {
                    "Lost" -> Color.Red.copy(alpha = 0.2f)
                    "Found" -> Color.Green.copy(alpha = 0.2f)
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )
            .padding(5.dp)
    ) {
        Text(
            text = status,
            style = MaterialTheme.typography.labelMedium.copy(
                color = when (status) {
                    "Lost" -> Color.Red
                    "Found" -> Color.Green
                    else -> MaterialTheme.colorScheme.onSurface
                },
                fontSize = 12.sp
            )
        )
    }
}
