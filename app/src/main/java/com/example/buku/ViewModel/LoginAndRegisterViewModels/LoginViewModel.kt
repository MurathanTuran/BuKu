package com.example.buku.ViewModel.LoginAndRegisterViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.buku.Model.User
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    var loginAuthErrorB = MutableLiveData<Boolean?>()
    var loginAuthError = MutableLiveData<String?>()

    var loginToContainerFragmentB = MutableLiveData<Boolean?>()

    fun login(user: User){
        auth.signInWithEmailAndPassword(user.email, user.password).addOnSuccessListener {
            loginToContainerFragmentB.value = true
        }.addOnFailureListener {
            loginAuthError.value = it.toString()
            loginAuthErrorB.value = true
        }
    }

}