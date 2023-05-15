package com.example.buku.View.MainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.buku.R
import com.example.buku.ViewModel.MainViewModels.SetPasswordViewModel
import com.example.buku.databinding.FragmentSetPasswordBinding

class SetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentSetPasswordBinding

    private lateinit var viewModel: SetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SetPasswordViewModel::class.java)

        buttonClicked()

        viewModel.emptyAreaErrorB.observe(viewLifecycleOwner, Observer {
            if(it == true){
                Toast.makeText(requireContext().applicationContext, viewModel.emptyAreaError.value, Toast.LENGTH_LONG).show()
                viewModel.emptyAreaErrorB.value = false
            }
        })

        viewModel.passwordsNotMatchedErrorB.observe(viewLifecycleOwner, Observer {
            if(it == true){
                Toast.makeText(requireContext().applicationContext, viewModel.passwordsNotMatchedError.value, Toast.LENGTH_LONG).show()
                viewModel.passwordsNotMatchedErrorB.value = false
            }
        })

        viewModel.snapshotListenerErrorB.observe(viewLifecycleOwner, Observer {
            if(it == true){
                Toast.makeText(requireContext().applicationContext, viewModel.snapshotListenerError.value, Toast.LENGTH_LONG).show()
                viewModel.snapshotListenerErrorB.value = false
            }
        })

        viewModel.loadFragmentB.observe(viewLifecycleOwner, Observer {
            if(it == true){
                Toast.makeText(requireContext().applicationContext, viewModel.passwordChangedMessage.value, Toast.LENGTH_LONG).show()
                loadFragment(HomeFragment())
                viewModel.loadFragmentB.value = false
            }
        })

    }

    private fun buttonClicked(){
        binding.setPasswordButtonSetPasswordFragment.setOnClickListener {
            val oldPassword = binding.oldPasswordSetPasswordFragment.text.toString()
            val oldPasswordConfirm = binding.confirmOldPasswordSetPasswordFragment.text.toString()
            val newPassword = binding.newPasswordSetPasswordFragment.text.toString()

            viewModel.setPassword(oldPassword, oldPasswordConfirm, newPassword)
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.layoutContainerFragment, fragment).commit()
    }

}