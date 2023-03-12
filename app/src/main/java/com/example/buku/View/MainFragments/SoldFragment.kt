package com.example.buku.View.MainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buku.databinding.FragmentSoldBinding

class SoldFragment : Fragment() {

    private lateinit var binding: FragmentSoldBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSoldBinding.inflate(inflater, container, false)
        return binding.root
    }

}