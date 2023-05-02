package com.example.buku.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buku.Model.Book
import com.example.buku.Model.FirebaseObject
import com.example.buku.R
import com.example.buku.Util.setImageWithUrl
import com.example.buku.View.MainFragments.BoughtFragment
import com.example.buku.View.MainFragments.HomeFragment
import com.example.buku.databinding.RecyclerRowBoughtFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class RecyclerAdapterBoughtFragment(val itemList: ArrayList<Book>, val fragmentManager: FragmentManager): RecyclerView.Adapter<RecyclerAdapterBoughtFragment.ViewHolder>() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    class ViewHolder(val binding: RecyclerRowBoughtFragmentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerRowBoughtFragmentBinding.inflate(LayoutInflater.from(parent.context.applicationContext), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.imageViewRecyclerRowBoughtFragment.setImageWithUrl(itemList[position].imageUrl)
        holder.binding.nameTextRecyclerRowBoughtFragment.setText(itemList[position].bookName)
        holder.binding.pointTextRecyclerRowBoughtFragment.setText(itemList[position].point)
        holder.binding.categoryTextRecyclerRowBoughtFragment.setText(itemList[position].category)
        holder.binding.stateTextRecyclerRowBoughtFragment.setText(itemList[position].status)
        holder.binding.numberOfPageTextRecyclerRowBoughtFragment.setText(itemList[position].numberOfPage)
        holder.binding.commentTextRecyclerRowBoughtFragment.setText(itemList[position].comment)

        holder.binding.iGotButtonRecyclerRowBoughtFragment.setOnClickListener {
            iGotRecyclerBoughtAdapter(itemList[position], holder)

        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    private fun iGotRecyclerBoughtAdapter(book: Book, holder: RecyclerAdapterBoughtFragment.ViewHolder){
        var userDataset: FirebaseObject
        var sellerDataset: FirebaseObject

        val userDocumentId: String
        val sellerDocumentId: String

        runBlocking {
            userDocumentId = firestore.collection("Users").whereEqualTo("email", auth.currentUser!!.email.toString()).get().await().documents[0].id
            userDataset = firestore.collection("Users").document(userDocumentId).get().await().toObject(
                FirebaseObject::class.java) as FirebaseObject

            sellerDocumentId = firestore.collection("Users").whereEqualTo("email", book.email).get().await().documents[0].id
            sellerDataset = firestore.collection("Users").document(sellerDocumentId).get().await().toObject(
                FirebaseObject::class.java) as FirebaseObject
        }
        val bookPoint = book.point?.toIntOrNull()?:0
        var sellerWallet = sellerDataset.wallet?.toIntOrNull()?:0
        val userWaitingConfirmation = userDataset.waitingConfirmation

        sellerWallet += bookPoint
        sellerDataset.wallet = sellerWallet.toString()

        for(i in 0 until userWaitingConfirmation!!.size){
            if(book.id == userWaitingConfirmation[i].id){
                userWaitingConfirmation.removeAt(i)
                break
            }
        }

        userDataset.waitingConfirmation = userWaitingConfirmation

        runBlocking {
            firestore.collection("Users").document(userDocumentId).set(userDataset).await()
            firestore.collection("Users").document(sellerDocumentId).set(sellerDataset).await()
        }
        loadFragment(HomeFragment(), fragmentManager)

    }

    private fun loadFragment(fragment: Fragment, fragmentManager: FragmentManager){
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.layoutContainerFragment, fragment).commit()
    }

}