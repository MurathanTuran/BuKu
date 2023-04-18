package com.example.buku.ViewModel.MainViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.buku.Model.Book
import com.example.buku.Model.FirebaseObject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class ProfileViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    var snapshotListenerErrorB = MutableLiveData<Boolean?>()
    var snapshotListenerError = MutableLiveData<String?>()

    fun getDataSetFromFirestore(): FirebaseObject{
        var firebaseObject = FirebaseObject()
        runBlocking {
            try {
                val value = firestore.collection("Users").whereEqualTo("email", auth.currentUser!!.email.toString()).get().await()
                firebaseObject = value.documents[0].toObject(FirebaseObject::class.java) as FirebaseObject
            }catch (e: Exception){
                snapshotListenerError.value = e.toString()
                snapshotListenerErrorB.value = true
            }
        }
        return firebaseObject
    }

}