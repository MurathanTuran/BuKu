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
import com.example.buku.Model.NewUser
import com.example.buku.ViewModel.LoginAndRegisterViewModels.RegisterViewModel
import com.example.buku.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private lateinit var viewModel: RegisterViewModel

    private var name = ""
    private var surname = ""
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        clicked()

        showErrors()
    }

    private fun clicked(){
        //register button clicked
        binding.registerButtonRegisterFragment.setOnClickListener {
            name = binding.nameTextRegisterFragment.text.toString()
            surname = binding.surnameTextRegisterFragment.text.toString()
            email = binding.emailTextRegisterFragment.text.toString()
            password = binding.passwordTextRegisterFragment.text.toString()

            createNewUser()
        }
    }

    private fun createNewUser(){
        if(!name.equals("") && !surname.equals("") && !email.equals("") && !password.equals("")){
            viewModel.register(NewUser(name, surname, email, password))
        }
        else{
            Toast.makeText(requireContext().applicationContext, "Alanları doldurunuz", Toast.LENGTH_LONG).show()
        }
    }

    private fun showErrors(){
        //auth error
        viewModel.registerAuthErrorB.observe(viewLifecycleOwner, Observer {
            if(it == true){
                Toast.makeText(requireContext().applicationContext, viewModel.registerAuthError.value, Toast.LENGTH_LONG).show()
                viewModel.registerAuthErrorB.value = false
            }
        })

        //firestore error
        viewModel.registerFirestoreErrorB.observe(viewLifecycleOwner, Observer {
            if(it == true){
                Toast.makeText(requireContext().applicationContext, viewModel.registerFirestoreError.value, Toast.LENGTH_LONG).show()
                viewModel.registerFirestoreErrorB.value = false
            }
        })

        //to container fragment
        viewModel.registerToContainerFragmentB.observe(viewLifecycleOwner, Observer {
            if(it == true){
                Navigation.findNavController(requireView()).navigate(RegisterFragmentDirections.actionRegisterFragmentToContainerFragment())
                viewModel.registerToContainerFragmentB.value = false
            }
        })
    }

}