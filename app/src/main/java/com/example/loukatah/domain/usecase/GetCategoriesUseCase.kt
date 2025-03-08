package com.example.loukatah.domain.usecase

import com.example.loukatah.data.model.ItemCategory
import com.example.loukatah.data.repository.ItemCategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetCategoriesUseCase @Inject constructor(
    private val itemCategoryRepository: ItemCategoryRepository
) {

     operator fun invoke(): Flow<List<ItemCategory>> =itemCategoryRepository.getCategories()

}
