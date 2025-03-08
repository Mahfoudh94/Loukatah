
# Task 3: Understanding Dependency Injection and Implementing Add Item Feature

## Dependency Injection with Dagger Hilt

The Loukatah app now uses Dagger Hilt for dependency injection across all layers of the application. Here's how it's structured:

### 1. Data Layer
- Repository implementations are provided via Hilt modules.
- Data sources and their dependencies are injected into repositories.
- Example: `ItemRepository` and `ItemCategoryRepository` are injected with their dependencies.

### 2. Domain Layer
- Use cases are injected with their required repositories.
- Examples:
  - `GetItemsUseCase` is injected with `ItemRepository`.
  - `AddItemUseCase` is injected with `ItemRepository`.
  - `GetCategoriesUseCase` is injected with `ItemCategoryRepository`.

### 3. Presentation Layer
- ViewModels are annotated with `@HiltViewModel`.
- Use cases are injected into ViewModels.
- Example: `ItemViewModel` is injected with `GetItemsUseCase`.

---

## Your Task: Implement Add Item Feature

### 1. Create Add Item View
Create a new composable function `AddItemScreen` in the presentation layer with the following requirements:

- Input fields for:
  - Title (Arabic)
  - Description
  - Category (dropdown selection)
  - Status selection (e.g., Found, Lost, Returned)
  - Picture URL
- Validation for all fields
- Submit button
- Success/Error feedback

### 2. Enhance AddItemViewModel

Update the `AddItemViewModel` to include the provided `AddItemUiState` and `AddItemEvent`:

```kotlin
data class AddItemUiState(
    val title: String = "",
    val description: String = "",
    val status: String = "",
    val picture: String = "",
    val category: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

sealed class AddItemEvent {
    data class TitleChange(val title: String): AddItemEvent()
    data class DescriptionChange(val description: String): AddItemEvent()
    data class StatusChange(val status: String): AddItemEvent()
    data class PictureChange(val picture: String): AddItemEvent()
    data class CategoryChange(val category: String): AddItemEvent()
    object SaveItem: AddItemEvent()
}
```

**Required functionality:**
1. State management for form fields.
2. Input validation.
3. Error handling.
4. Integration with `AddItemUseCase`.
5. Success/failure feedback.

---

### Implementation Steps

1. **AddItemViewModel Implementation**
    - Add state management using `StateFlow`.
    - Implement event handling for `AddItemEvent`.
    - Add validation logic (e.g., non-empty fields).
    - Integrate with `AddItemUseCase` to persist data.

2. **AddItemScreen Implementation**
    - Create UI components using Jetpack Compose.
    - Connect UI events to `AddItemViewModel`.
    - Show loading state.
    - Display error messages.
    - Show success confirmation.

3. **Navigation Integration**
    - Add navigation to Add Item screen (integrated in bottom navigation).
    - Handle back navigation.
    - Pass results back to previous screen.

---

### Success Criteria

Your implementation should:
1. Successfully add new items to the repository.
2. Validate all input fields.
3. Show appropriate loading states.
4. Handle and display errors.
5. Provide success feedback.
6. Follow MVVM architecture patterns.
7. Properly utilize dependency injection.

---

### Tips
- Use `viewModelScope` for coroutines in the ViewModel.
- Implement proper error handling (e.g., toast messages).
- Follow Material Design guidelines for the UI.
- Use RTL layout for Arabic text.
- Ensure proper dependency injection via Hilt.

---

### Bonus Challenges
1. Add image upload functionality instead of URL.
2. Implement form state persistence.
3. Add unit tests for ViewModel.
4. Add UI tests for the Add Item screen.
5. Implement real-time validation.

---

### Add Item UI Design

The Add Item screen should follow the app's existing design language:
- Maintain right-to-left (RTL) layout for Arabic text.
- Follow existing card and input field styles.
- Use consistent typography and spacing.

