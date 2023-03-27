package com.example.buku.View.LoginAndRegisterFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.buku.databinding.FragmentLoginOrRegisterBinding

class LoginOrRegisterFragment : Fragment() {

    private lateinit var binding: FragmentLoginOrRegisterBinding

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