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
import com.example.buku.Adapter.RecyclerAdapterBoughtFragment
import com.example.buku.ViewModel.MainViewModels.BoughtViewModel
import com.example.buku.databinding.FragmentBoughtBinding

class BoughtFragment : Fragment() {

    private lateinit var binding: FragmentBoughtBinding

    private lateinit var viewModel: BoughtViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoughtBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(BoughtViewModel::class.java)

        viewModel.snapshotListenerErrorB.observe(viewLifecycleOwner, Observer {
            if(it==true){
                Toast.makeText(requireContext().applicationContext, viewModel.snapshotListenerError.value.toString(), Toast.LENGTH_LONG).show()
                viewModel.snapshotListenerErrorB.value = false
            }
        })

        val layoutManager = LinearLayoutManager(context)
        binding.upRecyclerViewBoughtFragment.layoutManager = layoutManager
        binding.upRecyclerViewBoughtFragment.adapter = RecyclerAdapterBoughtFragment(viewModel.getDataSetFromFirestore(), requireActivity().supportFragmentManager)

    }

}