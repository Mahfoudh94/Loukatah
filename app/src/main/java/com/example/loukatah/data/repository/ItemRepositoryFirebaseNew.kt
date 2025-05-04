package com.example.loukatah.data.repository

import com.example.loukatah.data.model.Item
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
/**
 * تنفيذ واجهة ItemRepository باستخدام Firebase Firestore
 * Implementation of ItemRepository using Firebase Firestore
 */
class ItemRepositoryFirebaseNew @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth

) : ItemRepository {
    // مجموعة العناصر في Firestore
    // The items collection in Firestore
    private val itemsCollection = firestore.collection("items")

    // تدفق لعرض العناصر
    // Flow for emitting items
    private val _itemsFlow = MutableSharedFlow<List<Item>>(replay = 1)

    // مستمع التغييرات الفورية
    // Real-time changes listener
    private var snapshotListener: ListenerRegistration? = null

    init {
        setupFirestoreListener() // تهيئة مستمع التغييرات الفورية | Initialize real-time listener
    }

    /**
     * إعداد مستمع التغييرات الفورية
     * Set up real-time changes listener
     */
    private fun setupFirestoreListener() {
        snapshotListener = itemsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                // معالجة الخطأ - Error handling
                return@addSnapshotListener
            }

            val itemsList = snapshot?.documents?.mapNotNull { doc ->
                doc.data?.let { Item.fromMap(it) }
            } ?: emptyList()

            CoroutineScope(Dispatchers.IO).launch {
                _itemsFlow.emit(itemsList)
            }
        }
    }

    override fun getItems(): Flow<List<Item>> = _itemsFlow

    override suspend fun addItem(item: Item) {
        try {
            itemsCollection.document(item.id).set(item.toMap()).await()
        } catch (e: Exception) {
            // معالجة الخطأ - Error handling
            throw e
        }
    }

    override suspend fun updateItem(item: Item) {
        if (item.userId != firebaseAuth.currentUser?.uid) {
            throw SecurityException("غير مصرح لك بتعديل هذا العنصر")
        }
        try {
            itemsCollection.document(item.id).update(item.toMap()).await()
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
        val listener = itemsCollection.document(itemId).addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            trySend(snapshot?.data?.let { Item.fromMap(it) })
        }

        awaitClose { listener.remove() }
    }

    override fun searchItems(query: String, category: String?, status: String?): Flow<List<Item>> = callbackFlow {
        var queryRef = itemsCollection.orderBy("title").limit(50)

        if (query.isNotBlank()) {
            queryRef = queryRef.whereGreaterThanOrEqualTo("title", query)
                .whereLessThanOrEqualTo("title", query + "\uf8ff")
        }

        if (!category.isNullOrBlank()) {
            queryRef = queryRef.whereEqualTo("item_category", category)
        }

        if (!status.isNullOrBlank()) {
            queryRef = queryRef.whereEqualTo("status", status)
        }

        val listener = queryRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val items = snapshot?.documents?.mapNotNull { doc ->
                doc.data?.let { Item.fromMap(it) }
            } ?: emptyList()

            trySend(items)
        }

        awaitClose { listener.remove() }
    }
}