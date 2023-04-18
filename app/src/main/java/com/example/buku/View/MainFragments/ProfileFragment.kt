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
import com.example.buku.Adapter.RecyclerAdapterProfileFragment
import com.example.buku.ViewModel.MainViewModels.ProfileViewModel
import com.example.buku.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        viewModel.snapshotListenerErrorB.observe(viewLifecycleOwner, Observer {
            if(it==true){
                Toast.makeText(requireContext().applicationContext, viewModel.snapshotListenerError.value.toString(), Toast.LENGTH_LONG).show()
                viewModel.snapshotListenerErrorB.value = false
            }
        })

        val firebaseObject = viewModel.getDataSetFromFirestore()

        binding.emailTextViewProfileFragment.text = firebaseObject.email
        binding.walletTextViewProfileFragment.text = firebaseObject.wallet

        firebaseObject.forSale.let {
            val layoutManager = LinearLayoutManager(context)
            binding.reyclerViewProfileFragment.layoutManager = layoutManager
            binding.reyclerViewProfileFragment.adapter = RecyclerAdapterProfileFragment(it!!)
        }

    }

}