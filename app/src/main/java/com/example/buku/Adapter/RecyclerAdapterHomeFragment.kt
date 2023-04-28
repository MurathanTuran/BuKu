package com.example.buku.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.buku.Model.Book
import com.example.buku.Model.FirebaseObject
import com.example.buku.R
import com.example.buku.Util.setImageWithUrl
import com.example.buku.View.MainFragments.BoughtFragment
import com.example.buku.databinding.RecyclerRowHomeFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class RecyclerAdapterHomeFragment(val itemList: ArrayList<Book>, val fragmentManager: FragmentManager): RecyclerView.Adapter<RecyclerAdapterHomeFragment.ViewHolder>() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    class ViewHolder(val binding: RecyclerRowHomeFragmentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerRowHomeFragmentBinding.inflate(LayoutInflater.from(parent.context.applicationContext), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.imageViewRecyclerRowHomeFragment.setImageWithUrl(itemList[position].imageUrl)
        holder.binding.nameTextRecyclerRowHomeFragment.setText(itemList[position].bookName)
        holder.binding.pointTextRecyclerRowHomeFragment.setText(itemList[position].point)
        holder.binding.categoryTextRecyclerRowHomeFragment.setText(itemList[position].category)
        holder.binding.stateTextRecyclerRowHomeFragment.setText(itemList[position].status)
        holder.binding.numberOfPageTextRecyclerRowHomeFragment.setText(itemList[position].numberOfPage)
        holder.binding.commentTextRecyclerRowHomeFragment.setText(itemList[position].comment)

        holder.binding.buyButtonRecyclerRowHomeFragment.setOnClickListener {
            buyBookRecyclerHomeAdapter(itemList[position], holder)

        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    private fun buyBookRecyclerHomeAdapter(book: Book, holder: ViewHolder){
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
        var userWallet = userDataset.wallet?.toIntOrNull()?:0
        val userWaitingConfirmation = userDataset.waitingConfirmation

        val sellerForSale = sellerDataset.forSale
        if(bookPoint<=userWallet){
            userWallet -= bookPoint
            userDataset.wallet = userWallet.toString()

            for(i in 0 until sellerForSale!!.size){
                println(book.id)
                println(sellerForSale[i].id)
                println(i)
                if(book.id == sellerForSale[i].id){
                    sellerForSale.removeAt(i)
                    break
                }
            }

            sellerDataset.forSale = sellerForSale

            userWaitingConfirmation?.add(book)
            userDataset.waitingConfirmation = userWaitingConfirmation

            runBlocking {
                firestore.collection("Users").document(userDocumentId).set(userDataset).await()
                firestore.collection("Users").document(sellerDocumentId).set(sellerDataset).await()
            }
            loadFragment(BoughtFragment(), fragmentManager)
        }
        else{
            Toast.makeText(holder.itemView.context.applicationContext, "Yetersiz Bakiye!!", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadFragment(fragment: Fragment, fragmentManager: FragmentManager){
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.layoutContainerFragment, fragment).commit()
    }

}