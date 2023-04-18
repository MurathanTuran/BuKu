package com.example.buku.View.MainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buku.Adapter.RecyclerAdapterHomeFragment
import com.example.buku.ViewModel.MainViewModels.HomeViewModel
import com.example.buku.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.snapshotListenerErrorB.observe(viewLifecycleOwner, Observer {
            if(it==true){
                Toast.makeText(requireContext().applicationContext, viewModel.snapshotListenerError.value.toString(), Toast.LENGTH_LONG).show()
                viewModel.snapshotListenerErrorB.value = false
            }
        })

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewHomeFragment.layoutManager = layoutManager
        binding.recyclerViewHomeFragment.adapter = RecyclerAdapterHomeFragment(viewModel.getDataSetFromFirestore())

    }

}