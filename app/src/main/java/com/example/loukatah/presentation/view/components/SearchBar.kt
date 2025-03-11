package com.example.loukatah.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Custom search bar component with menu button outside
 *
 * @param searchQuery Current search query text
 * @param onSearchQueryChange Callback when search query changes
 * @param onClearClick Callback when clear button is clicked
 * @param onMenuClick Callback when menu button is clicked
 * @param modifier Modifier for customizing the layout
 */
@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // حقل البحث مع الأيقونات
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(50.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp),
            singleLine = true,
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.clickable { /* يمكن تنفيذ إجراء البحث هنا إذا لزم الأمر */ },
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = "Search items...",
                                color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                            )
                        }
                        innerTextField()
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    if (searchQuery.isNotEmpty()) {
                        Icon(
                            modifier = Modifier.clickable { onClearClick() },
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear search",
                        )
                    } else {
                        Icon(
                            modifier = Modifier.clickable {},
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter",
                        )
                    }
                }
            }
        )
//        Spacer(modifier = Modifier.width(12.dp))

//        IconButton(onClick = onMenuClick) {
//            Icon(
//                imageVector = Icons.Default.Menu,
//                contentDescription = "Menu"
//            )
//        }
    }
}