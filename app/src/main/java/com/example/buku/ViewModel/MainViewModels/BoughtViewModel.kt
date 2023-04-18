package com.example.buku.ViewModel.MainViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.buku.Model.Book
import com.example.buku.Model.FirebaseObject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class BoughtViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    var snapshotListenerErrorB = MutableLiveData<Boolean?>()
    var snapshotListenerError = MutableLiveData<String?>()

    fun getDataSetFromFirestore(): ArrayList<Book>{
        val itemList = ArrayList<Book>()
        runBlocking {
            try {
                val value = firestore.collection("Users").whereEqualTo("email", auth.currentUser!!.email.toString()).get().await()
                for(document in value.documents){
                    val firebaseObject = document.toObject(FirebaseObject::class.java) as FirebaseObject
                    itemList.addAll(firebaseObject.waitingConfirmation as ArrayList<Book>)
                }
            }catch (e: Exception){
                snapshotListenerError.value = e.toString()
                snapshotListenerErrorB.value = true
            }
        }
        return itemList
    }

}