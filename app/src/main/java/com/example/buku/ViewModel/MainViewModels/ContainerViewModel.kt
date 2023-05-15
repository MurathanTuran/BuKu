package com.example.buku.ViewModel.MainViewModels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ContainerViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    fun logout(){
        auth.signOut()
    }

}