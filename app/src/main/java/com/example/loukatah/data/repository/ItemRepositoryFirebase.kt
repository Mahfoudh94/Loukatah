package com.example.loukatah.data.repository

import android.util.Log
import com.example.loukatah.data.model.Item
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


class ItemRepositoryFirebase @Inject constructor() : ItemRepository {
    private val itemsCollection = Firebase.firestore.collection("items")
    // Flow for emitting items
    private val _itemsFlow = MutableSharedFlow<List<Item>>(replay = 1)

    // مستمع التغييرات الفورية
    // Real-time changes listener
    private var snapshotListener: ListenerRegistration? = null

    init {
        setupFirestoreListener() // تهيئة مستمع التغييرات الفورية | Initialize real-time listener
    }

    private fun setupFirestoreListener() {
        snapshotListener = itemsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                // معالجة الخطأ - Error handling
                return@addSnapshotListener
            }

            val itemsList = snapshot?.documents?.mapNotNull { doc ->
                doc.data?.let { Item.fromMap(it,doc.id) }
            } ?: emptyList()

            CoroutineScope(Dispatchers.IO).launch {
                _itemsFlow.emit(itemsList)
            }
        }
    }

    override fun getItems(): Flow<List<Item>> = _itemsFlow

    override suspend fun addItem(item: Item) {
        try {
            // Add the item to the Firestore collection
            itemsCollection.add(item).await()
            // Optionally, update the local list or emit a success state
        } catch (e: Exception) {
            // Handle errors (e.g., network issues)
            // Optionally, emit an error state
        }
    }

    override suspend fun updateItem(item: Item) {
        try {
            itemsCollection.document(item.idDoc).update(item.toMap()).await()
        } catch (e: Exception) {
            // معالجة الخطأ - Error handling
            throw Exception("فشل تحديث العنصر: ${e.message}")
        }
    }

    override suspend fun deleteItem(itemId: String) {
        try {
            itemsCollection.document(itemId).delete().await()
        } catch (e: Exception) {
            // معالجة الخطأ - Error handling
            throw e
        }
    }
    override fun getItemById(itemId: String): Flow<Item?> = callbackFlow {
    }

    override fun searchItems(query: String, category: String?, status: String?): Flow<List<Item>> = callbackFlow {
    }
}
