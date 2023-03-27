package com.example.buku.View.LoginAndRegisterFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.buku.Model.User
import com.example.buku.ViewModel.LoginAndRegisterViewModels.LoginViewModel
import com.example.buku.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var viewModel: LoginViewModel

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        clicked()

        showErrors()
    }

    private fun clicked(){
        //login button clicked
        binding.loginButtonLoginFragment.setOnClickListener {
            email = binding.emailTextLoginFragment.text.toString()
            password = binding.passwordTextLoginFragment.text.toString()

            loginUser()
        }
    }

    private fun loginUser(){
        if(!email.equals("") && !password.equals("")){
            viewModel.login(User(email, password))
        }
        else{
            Toast.makeText(requireContext().applicationContext, "Alanları doldurunuz", Toast.LENGTH_LONG).show()
        }
    }

    private fun showErrors(){
        //auth error
        viewModel.loginAuthErrorB.observe(viewLifecycleOwner, Observer {
            if(it == true){
                Toast.makeText(requireContext().applicationContext, viewModel.loginAuthError.value, Toast.LENGTH_LONG).show()
            }
        })

        //to container fragment
        viewModel.loginToContainerFragmentB.observe(viewLifecycleOwner, Observer {
            if(it == true){
                Navigation.findNavController(requireView()).navigate(LoginFragmentDirections.actionLoginFragmentToContainerFragment())
            }
        })
    }

}