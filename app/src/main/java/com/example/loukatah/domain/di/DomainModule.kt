package com.example.loukatah.domain.di

import com.example.loukatah.data.repository.ItemCategoryRepository
import com.example.loukatah.data.repository.ItemRepository
import com.example.loukatah.domain.usecase.AddItemUseCase
import com.example.loukatah.domain.usecase.GetCategoriesUseCase
import com.example.loukatah.domain.usecase.GetItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetItemsUseCase(itemRepository: ItemRepository): GetItemsUseCase {
        return GetItemsUseCase(itemRepository)
    }


    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(itemCategoryRepository: ItemCategoryRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(itemCategoryRepository)
    }

    @Provides
    @Singleton
    fun provideAddItemUseCase(itemRepository: ItemRepository): AddItemUseCase {
        return AddItemUseCase(itemRepository)
    }


}
