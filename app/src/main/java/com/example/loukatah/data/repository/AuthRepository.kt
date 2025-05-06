package com.example.loukatah.data.repository


import com.example.loukatah.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    fun login(email: String, password: String): Flow<Resource<FirebaseUser?>> = flow {
        emit(Resource.Loading())
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result.user))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Login failed"))
        }
    }

    fun signup(email: String, password: String): Flow<Resource<FirebaseUser?>> = flow {
        emit(Resource.Loading())
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result.user))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Signup failed"))
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }
}