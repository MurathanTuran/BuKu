package com.example.buku.ViewModel.LoginAndRegisterViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.buku.Model.NewUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    var registerAuthErrorB = MutableLiveData<Boolean?>()
    var registerAuthError = MutableLiveData<String?>()

    var registerFirestoreErrorB = MutableLiveData<Boolean?>()
    var registerFirestoreError = MutableLiveData<String?>()

    var registerToContainerFragmentB = MutableLiveData<Boolean?>()

    fun register(newUser: NewUser){
        auth.createUserWithEmailAndPassword(newUser.email, newUser.password).addOnSuccessListener {
            val user = HashMap<String, Any>()
            user.put("email", newUser.email)
            user.put("name", newUser.name)
            user.put("surname", newUser.surname)

            firestore.collection("Users").add(user).addOnSuccessListener {
                registerToContainerFragmentB.value = true
            }.addOnFailureListener {
                registerFirestoreError.value = it.toString()
                registerFirestoreErrorB.value = true
            }
        }.addOnFailureListener {
            registerAuthError.value = it.toString()
            registerAuthErrorB.value = true
        }
    }

}