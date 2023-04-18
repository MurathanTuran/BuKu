package com.example.buku.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.buku.Model.Book
import com.example.buku.Util.setImageWithUrl
import com.example.buku.databinding.RecyclerRowProfileFragmentBinding

class RecyclerAdapterProfileFragment(val itemList: ArrayList<Book>): RecyclerView.Adapter<RecyclerAdapterProfileFragment.ViewHolder>() {

    class ViewHolder(val binding: RecyclerRowProfileFragmentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterProfileFragment.ViewHolder {
        val binding = RecyclerRowProfileFragmentBinding.inflate(LayoutInflater.from(parent.context.applicationContext), parent, false)
        return RecyclerAdapterProfileFragment.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterProfileFragment.ViewHolder, position: Int) {
        holder.binding.imageViewRecyclerRowProfileFragment.setImageWithUrl(itemList[position].imageUrl)
        holder.binding.nameTextRecyclerRowProfileFragment.setText(itemList[position].bookName)
        holder.binding.pointTextRecyclerRowProfileFragment.setText(itemList[position].point)
        holder.binding.categoryTextRecyclerRowProfileFragment.setText(itemList[position].category)
        holder.binding.stateTextRecyclerRowProfileFragment.setText(itemList[position].status)
        holder.binding.numberOfPageTextRecyclerRowProfileFragment.setText(itemList[position].numberOfPage)
        holder.binding.commentTextRecyclerRowProfileFragment.setText(itemList[position].comment)    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}