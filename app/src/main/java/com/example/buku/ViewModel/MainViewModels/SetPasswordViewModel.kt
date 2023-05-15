package com.example.buku.ViewModel.MainViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class SetPasswordViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    var emptyAreaErrorB = MutableLiveData<Boolean?>()
    var emptyAreaError = MutableLiveData<String?>()
    var passwordsNotMatchedErrorB = MutableLiveData<Boolean?>()
    var passwordsNotMatchedError = MutableLiveData<String?>()
    var snapshotListenerErrorB = MutableLiveData<Boolean?>()
    var snapshotListenerError = MutableLiveData<String?>()
    var loadFragmentB = MutableLiveData<Boolean?>()
    var passwordChangedMessage = MutableLiveData<String?>()

    fun setPassword(oldPassword: String, oldPasswordConfirm: String, newPassword: String){
        if(!newPassword.equals("") && !oldPasswordConfirm.equals("") && !oldPassword.equals("")){
            if(oldPassword.equals(oldPasswordConfirm)){
                val currentUser = auth.currentUser
                val credential = EmailAuthProvider.getCredential(currentUser!!.email.toString(), oldPassword)
                currentUser.reauthenticate(credential).addOnSuccessListener {
                    currentUser.updatePassword(newPassword).addOnSuccessListener {
                        loadFragmentB.value = true
                        passwordChangedMessage.value = "Şifreniz değiştirildi."
                    }.addOnFailureListener {
                        snapshotListenerErrorB.value = true
                        snapshotListenerError.value = it.localizedMessage
                    }
                }.addOnFailureListener {
                    snapshotListenerErrorB.value = true
                    snapshotListenerError.value = it.localizedMessage
                }
            }
            else{
                passwordsNotMatchedErrorB.value = true
                passwordsNotMatchedError.value = "Şifreleriniz eşleşmedi."
            }
        }
        else{
            emptyAreaErrorB.value = true
            emptyAreaError.value = "Boş alanları doldurunuz."
        }
    }

}