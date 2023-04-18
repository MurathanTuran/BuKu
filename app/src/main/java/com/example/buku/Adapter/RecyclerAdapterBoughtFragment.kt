package com.example.buku.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.buku.Model.Book
import com.example.buku.Util.setImageWithUrl
import com.example.buku.databinding.RecyclerRowBoughtFragmentBinding

class RecyclerAdapterBoughtFragment(val itemList: ArrayList<Book>): RecyclerView.Adapter<RecyclerAdapterBoughtFragment.ViewHolder>() {

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
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}