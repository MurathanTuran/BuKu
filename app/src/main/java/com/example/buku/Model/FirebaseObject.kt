package com.example.buku.Model

data class FirebaseObject(
    val email: String,
    val name: String,
    val surname: String,
    val isUserNew: Boolean,
    val wallet: String,
    val forSale: ArrayList<Book>,
    val waitingConfirmation: ArrayList<Book>
)
