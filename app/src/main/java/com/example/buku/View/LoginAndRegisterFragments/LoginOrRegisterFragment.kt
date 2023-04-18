package com.example.buku.View.LoginAndRegisterFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.buku.ViewModel.LoginAndRegisterViewModels.LoginOrRegisterViewModel
import com.example.buku.databinding.FragmentLoginOrRegisterBinding

class LoginOrRegisterFragment : Fragment() {

    private lateinit var binding: FragmentLoginOrRegisterBinding

    private lateinit var viewModel: LoginOrRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginOrRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clicked()

        viewModel = ViewModelProvider(this).get(LoginOrRegisterViewModel::class.java)

        viewModel.checkCurrentUser()

        viewModel.currentUserB.observe(viewLifecycleOwner, Observer {
            if(viewModel.currentUserB.value == true){
                Navigation.findNavController(requireView()).navigate(LoginOrRegisterFragmentDirections.actionLoginOrRegisterFragmentToContainerFragment())
                viewModel.currentUserB.value = false
            }
        })
    }

    private fun clicked(){
        //to register page
        binding.registerButtonLoginOrRegisterFragment.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(LoginOrRegisterFragmentDirections.actionLoginOrRegisterFragmentToRegisterFragment())
        }

        //to login page
        binding.loginButtonLoginOrRegisterFragment.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(LoginOrRegisterFragmentDirections.actionLoginOrRegisterFragmentToLoginFragment())
        }
    }
}