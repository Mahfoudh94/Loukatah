package com.example.loukatah.data.di

import com.example.loukatah.data.repository.ItemCategoryRepository
import com.example.loukatah.data.repository.ItemRepository
import com.example.loukatah.data.repository.ItemCategoryRepositoryImpl
import com.example.loukatah.data.repository.ItemRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    

    @Provides
    @Singleton
    fun provideItemRepository(): ItemRepository {
        return ItemRepositoryImpl()
    }
    

    @Provides
    @Singleton
    fun provideItemCategoryRepository(): ItemCategoryRepository {
        return ItemCategoryRepositoryImpl()
    }
}
