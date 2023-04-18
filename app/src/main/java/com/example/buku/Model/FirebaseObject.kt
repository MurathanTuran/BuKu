package com.example.buku.Model

data class FirebaseObject(
    val email: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val isUserNew: Boolean? = null,
    val wallet: String? = null,
    val forSale: ArrayList<Book>? = null,
    val waitingConfirmation: ArrayList<Book>? = null
)
