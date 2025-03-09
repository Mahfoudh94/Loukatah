package com.example.loukatah.view

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.loukatah.model.Item
import com.example.loukatah.viewmodel.ItemCategoryViewModel
import com.example.loukatah.viewmodel.ItemViewModel
import java.text.SimpleDateFormat

@Composable
fun MainScreen(itemViewModel: ItemViewModel, itemCategoryViewModel: ItemCategoryViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        val itemState by itemViewModel.uiState.collectAsState()
        val categoryState by itemCategoryViewModel.categoryState.collectAsState()
        var textInput by remember { mutableStateOf("") }
        var selected by remember { mutableIntStateOf(0) }
        val lightBlue=Color(0xFF87CEFA)
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f) // يجعل حقل البحث يأخذ المساحة المتبقية
                            .clip(RoundedCornerShape(50.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.clickable { },
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        BasicTextField(
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            value = textInput,
                            onValueChange = {
                                textInput = it
                                when (selected) {
                                    0 -> itemViewModel.getItems(1, textInput)
                                    1 -> itemViewModel.getItems(2, textInput)
                                    else -> itemViewModel.getItems(3, textInput)
                                }
                            },
                            textStyle = TextStyle(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 18.sp
                            ),
                            decorationBox = { innerTextField ->
                                Box {
                                    if (textInput.isEmpty()) {
                                        Text(
                                            text = "Search items...",
                                            color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        if (textInput.isNotEmpty()) {
                            Icon(
                                modifier = Modifier.clickable {
                                    textInput = ""
                                    when (selected) {
                                        0 -> itemViewModel.getItems(1, textInput)
                                        1 -> itemViewModel.getItems(2, textInput)
                                        else -> itemViewModel.getItems(3, textInput)
                                    }
                                },
                                imageVector = Icons.Default.Clear,
                                contentDescription = null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // أيقونة الفلترة خارج حقل البحث
                    IconButton(onClick = { /* تنفيذ الفلترة */ }) {
                        Icon(
                            imageVector = Icons.Default.FilterList, // تغيير الأيقونة إلى أيقونة الفلترة المناسبة
                            contentDescription = "Filter",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                /*  Row(){
                      BasicTextField(
                          modifier = Modifier
                              .fillMaxWidth()
                              .padding(horizontal = 20.dp)
                              .clip(RoundedCornerShape(50.dp))
                              .background(MaterialTheme.colorScheme.primaryContainer)
                              .padding(16.dp),
                          singleLine = true,
                          value = textInput,
                          onValueChange = { textInput = it
                                          },
                          textStyle = TextStyle(
                              color = MaterialTheme.colorScheme.onBackground,
                              fontSize = 18.sp
                          ),
                          decorationBox = { innerTextField ->
                              Row( modifier = Modifier
                                  .fillMaxWidth(),
                                  verticalAlignment = Alignment.CenterVertically){
                                  Icon(
                                      modifier = Modifier.clickable {
                                      },
                                      imageVector = Icons.Default.Search,
                                      contentDescription = null,
                                  )

                                  Spacer(modifier = Modifier.width(8.dp))

                                  Box( modifier = Modifier
                                      .weight(1f)){
                                      if( textInput.isEmpty()){
                                          Text(
                                              text = "Search items...",
                                              color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                          )
                                      }
                                      innerTextField()
                                  }

                                  Spacer(modifier = Modifier.width(8.dp))

                                  if(textInput.isNotEmpty()) {

                                      Icon(
                                          modifier = Modifier.clickable {
                                              textInput = ""
                                              if(selected == 0){
                                                  itemViewModel.getItems(1 , textInput)
                                              }else if(selected == 1){
                                                  itemViewModel.getItems(2 , textInput)
                                              }else{
                                                  itemViewModel.getItems(3 , textInput)
                                              }

                                          },

                                          imageVector = Icons.Default.Clear,
                                          contentDescription = null,
                                      )
                                  }else{
                                      Icon(
                                          modifier = Modifier.clickable {

                                          },
                                          imageVector = Icons.Default.FilterList,
                                          contentDescription = null,
                                      )
                                  }
                              }
                          }
                      )
                  }         */
            },

            floatingActionButton = {
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(40.dp))
                    .background(lightBlue)
                    .clickable {  }
                    .size(60.dp),
                    contentAlignment = Alignment.Center){
                    Icon(
                        modifier = Modifier.size(50.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            },
            bottomBar = {
                NavigationBar {
                    categoryState.categories.forEachIndexed { index, itemCategory ->
                        NavigationBarItem(
                            selected = index == selected,
                            onClick = {
                                selected = index
                                when (itemCategory.id) {
                                    "1" -> {
                                        itemViewModel.getItems(1, "")
                                    }
                                    "2" -> {
                                        itemViewModel.getItems(2, "")
                                    }
                                    "3" -> {
                                        itemViewModel.getItems(3, "")
                                    }
                                    else -> {
                                        itemViewModel.getItems(4, "")
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selected)
                                        itemCategory.selectedIcon
                                    else
                                        itemCategory.unselectedIcon,
                                    contentDescription = itemCategory.name,
                                )
                            },
                            label = {
                                // Display text only if the item is selected
                                if (index == selected) {
                                    Text(text = itemCategory.name)
                                }
                            }
                        )
                    }
                }

            }
        ){ paddingValues ->
            LazyColumn( modifier = Modifier.padding(paddingValues)) {
                when {
                    itemState.isLoading -> {
                        item {
                            Column {
                                repeat(7){
                                    AnimatedShimmer()
                                }
                            }
                        }
                    }
                    itemState.items.isEmpty() -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "No items found")
                            }
                        }
                    }
                    else -> {
                        items(itemState.items) { item ->
                            CardItem(item = item)
                        }
                    }
                }
            }
        }


    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun CardItem(item: Item) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Row(modifier = Modifier
            .padding(16.dp)
            .clickable {  },
            verticalAlignment = Alignment.CenterVertically ) {
            AsyncImage(
                model = item.picture,
                contentDescription = item.title,
                modifier = Modifier
                    .width(80.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.padding(10.dp)){
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(
                        modifier = Modifier.weight(1f),
                        text = item.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier
                        .padding(5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background( when (item.status) {
                            "Lost" -> Color.Red.copy(alpha =0.2f)
                            "Found" -> Color.Green.copy(alpha =0.2f)
                            else -> MaterialTheme.colorScheme.onSurface
                        })
                        .padding(5.dp)
                    ){
                        Text(
                            text = item.status,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = when (item.status) {
                                    "Lost" -> Color.Red
                                    "Found" -> Color.Green
                                    else -> MaterialTheme.colorScheme.onSurface
                                },
                                fontSize = 12.sp
                            )
                        )
                    }
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
@Composable
fun ShimmerLoading(brush : Brush){
    Row(modifier = Modifier
        .padding(all = 10.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically ){

        Spacer(modifier = Modifier
            .width(80.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(brush))

        Spacer(modifier = Modifier.width(8.dp))

        Column(verticalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier
                .height(25.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth(fraction = 0.9f)
                .background(brush))

            Spacer(modifier = Modifier.padding(5.dp))

            Spacer(modifier = Modifier
                .height(25.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth(fraction = 0.7f)
                .background(brush))
        }
    }
}
@Composable
fun AnimatedShimmer(){
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value , y = translateAnim.value)
    )

    ShimmerLoading(brush = brush)
}