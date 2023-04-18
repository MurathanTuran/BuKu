package com.example.buku.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.buku.Model.Book
import com.example.buku.Util.setImageWithUrl
import com.example.buku.databinding.RecyclerRowHomeFragmentBinding

class RecyclerAdapterHomeFragment(val itemList: ArrayList<Book>): RecyclerView.Adapter<RecyclerAdapterHomeFragment.ViewHolder>() {

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
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}