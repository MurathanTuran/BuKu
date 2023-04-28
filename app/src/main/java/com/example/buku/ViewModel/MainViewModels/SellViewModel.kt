package com.example.buku.ViewModel.MainViewModels

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.net.Uri
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.buku.Model.Book
import com.example.buku.Model.FirebaseObject
import com.example.buku.R
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.UUID

class SellViewModel: ViewModel() {


    lateinit var permissionLauncher: ActivityResultLauncher<String>
    lateinit var activityLauncher: ActivityResultLauncher<Intent>

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    var emptyAreaErrorB = MutableLiveData<Boolean?>()
    var snapshotListenerErrorB = MutableLiveData<Boolean?>()
    var snapshotListenerError = MutableLiveData<String?>()
    var loadFragmentB = MutableLiveData<Boolean?>()

    private var point = 0

    fun addSalesPost(imageUri: Uri?, bookName: String, category: String, state: String, numberOfPage: String, comment: String){
        point = 0

        if(imageUri==null || bookName.equals("") || category.equals("") || state.equals("") || numberOfPage.equals("")){
            emptyAreaErrorB.value = true
        }
        else {
            emptyAreaErrorB.value = false

            categoryPoint(category)
            statePoint(state)
            numberOfPagePoint(numberOfPage)

           runBlocking {
               try {
                   val uuid = UUID.randomUUID()
                   val storageRef = storage.reference
                   val imageRef = storageRef.child("images/$uuid.jpg")

                   imageRef.putFile(imageUri).await()

                   val imageUrl = imageRef.downloadUrl.await().toString()

                   val documentId = firestore.collection("Users").whereEqualTo("email", auth.currentUser!!.email.toString()).get().await().documents[0].id

                   val forSale = firestore.collection("Users").document(documentId).get().await().get("forSale") as ArrayList<Book>

                   val book = Book(imageUrl, bookName, point.toString(), category, state, numberOfPage, comment, auth.currentUser!!.email.toString(), UUID.randomUUID().toString())

                   forSale.add(book)

                   firestore.collection("Users").document(documentId).update("forSale", forSale).await()

                   loadFragmentB.value = true
               }catch (e: Exception){
                   snapshotListenerError.value = e.toString()
                   snapshotListenerErrorB.value = true
               }
           }

        }
    }

    fun selectImage(context: Context, activity: Activity, view: View){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(view, "Permission Needed", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", View.OnClickListener {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }).show()
            }
            else{
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
        else{
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityLauncher.launch(intentToGallery)
        }
    }

    private fun categoryPoint(category: String){
        if(category.equals("Test Kitabı")){
            point+=2
        }else if(category.equals("Okuma Kitabı")){
            point+=1
        }
    }

    private fun statePoint(state: String){
        if(state.equals("Yeni")){
            point+=3
        }else if(state.equals("Kullanılmış")){
            point+=2
        }else if(state.equals("Eski")){
            point+=1
        }
    }

    private fun numberOfPagePoint(numberOfPage: String){
        if(numberOfPage.equals("100'den Az")){
            point+=1
        }else if(numberOfPage.equals("100-200")){
            point+=2
        }else if(numberOfPage.equals("200-300")){
            point+=3
        }else if(numberOfPage.equals("300'den Fazla")){
            point+=4
        }
    }

}