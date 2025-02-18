import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loukatah.model.Item
import com.example.loukatah.model.ItemCategory
import com.example.loukatah.viewmodel.ItemCategoryViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemBottomSheet(
    onItemAdded: (Item) -> Unit,
    itemCategoryViewModel: ItemCategoryViewModel = viewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Found") }
    var selectedCategory by remember { mutableStateOf<ItemCategory?>(null) }
    var imageUrl by remember { mutableStateOf("") }
    val categoryState by itemCategoryViewModel.categoryState.collectAsState()
    val categories = categoryState.categories

    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val currentDate = dateFormat.format(Date())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Add New Item", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            singleLine = false,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("Image URL") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Status Selection
        Row {
            Text(text = "Status:", modifier = Modifier.padding(end = 8.dp))
            Button(
                onClick = { status = "Found" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (status == "Found") Color.Green else Color(0xFFEAF8EA)
                )
            ) {
                Text(text = "Found")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = { status = "Lost" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (status == "Lost") Color.Red else Color(0xFFFFEAEA)
                )
            ) {
                Text(text = "Lost")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ✅ Category Selection using ExposedDropdownMenuBox
        Text(text = "Select Category")
        var isDropdownExpanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = isDropdownExpanded,
            onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
        ) {
            OutlinedTextField(
                value = selectedCategory?.name ?: "Select a category",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                }
            )

            ExposedDropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.name) },
                        onClick = {
                            selectedCategory = category
                            isDropdownExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val newItem = Item(
                    id = UUID.randomUUID().toString(), // Generate unique ID
                    title = title,
                    description = description,
                    status = status,
                    picture = imageUrl.ifBlank { null },
                    coordinates = Pair(0.0, 0.0), // Dummy coordinates
                    createdAt = currentDate,
                    updatedAt = currentDate,
                    date_lost = if (status == "Lost") currentDate else null,
                    item_category = (selectedCategory ?: categories.firstOrNull() ?: return@Button).toString() // Default category
                )
                onItemAdded(newItem)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add Item")
        }
    }
}
