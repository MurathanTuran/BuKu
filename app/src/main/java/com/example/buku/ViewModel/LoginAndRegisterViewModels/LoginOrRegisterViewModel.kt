package com.example.buku.ViewModel.LoginAndRegisterViewModels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginOrRegisterViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    var currentUserB = MediatorLiveData<Boolean>()

    fun checkCurrentUser(){
        if(auth.currentUser != null){
            currentUserB.value = true
        }
    }
}