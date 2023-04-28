package com.example.buku.Model

data class FirebaseObject(
    val email: String? = null,
    val name: String? = null,
    val surname: String? = null,
    var isUserNew: Boolean? = null,
    var wallet: String? = null,
    var forSale: ArrayList<Book>? = null,
    var waitingConfirmation: ArrayList<Book>? = null
)
