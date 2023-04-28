package com.example.buku.View.MainFragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.buku.R
import com.example.buku.ViewModel.MainViewModels.SellViewModel
import com.example.buku.databinding.FragmentSellBinding

class SellFragment : Fragment() {

    private lateinit var binding: FragmentSellBinding

    private lateinit var viewModel: SellViewModel

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(SellViewModel::class.java)

        registerLauncher()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSellBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonClicked()

        viewModel.snapshotListenerErrorB.observe(viewLifecycleOwner, Observer {
            if(it==true){
                Toast.makeText(requireContext().applicationContext, viewModel.snapshotListenerError.value.toString(), Toast.LENGTH_LONG).show()
                viewModel.snapshotListenerErrorB.value = false
            }
        })

        viewModel.emptyAreaErrorB.observe(viewLifecycleOwner, Observer {
            if(it==true){
                Toast.makeText(requireContext().applicationContext, "Alanları Doldurunuz..", Toast.LENGTH_LONG).show()
                viewModel.emptyAreaErrorB.value = false
            }
        })

        viewModel.loadFragmentB.observe(viewLifecycleOwner, Observer {
            if(it==true){
                loadFragment(ProfileFragment())
                viewModel.loadFragmentB.value = false
            }
        })
    }

    private fun registerLauncher(){
        viewModel.permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                viewModel.activityLauncher.launch(intentToGallery)
            }
            else{
                Toast.makeText(requireContext().applicationContext, "Permission Needed", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.activityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                val intentFromResult = it.data
                if(intentFromResult!=null){
                    val uri = intentFromResult.data
                    if(uri!=null){
                        binding.imageViewBuyFragment.setImageURI(uri)
                        imageUri = uri
                    }
                }
            }
        }
    }

    private fun buttonClicked(){
        binding.imageViewBuyFragment.setOnClickListener {
            viewModel.selectImage(requireContext(), requireActivity(), requireView())
        }

        binding.buyButtonBuyFragment.setOnClickListener{
            viewModel.addSalesPost(imageUri, getBookName(), getCategory(), getState(), getNumberOfPage(), getComment())
        }

    }

    private fun getBookName(): String{
        return binding.bookNameTextBuyFragment.text.toString()
    }

    private fun getCategory(): String{
        if(binding.categoryRadioGroupBuyFragment.checkedRadioButtonId == binding.testBookRadioButtonBuyFragment.id)  return "Test Kitabı"
        else if (binding.categoryRadioGroupBuyFragment.checkedRadioButtonId == binding.readingBookRadioButtonBuyFragment.id) return "Okuma Kitabı"
        else return ""
    }

    private fun getState(): String{
        if(binding.stateRadioGroupBuyFragment.checkedRadioButtonId == binding.newRadioButtonBuyFragment.id) return "Yeni"
        else if(binding.stateRadioGroupBuyFragment.checkedRadioButtonId == binding.usedRadioButtonBuyFragment.id) return "Kullanılmış"
        else if(binding.stateRadioGroupBuyFragment.checkedRadioButtonId == binding.oldRadioButtonBuyFragment.id) return "Eski"
        else return ""
    }

    private fun getNumberOfPage(): String{
        if(binding.numberOfPageRadioGroupBuyFragment.checkedRadioButtonId == binding.lessThanHundredRadioButtonBuyFragment.id) return "100'den Az"
        else if(binding.numberOfPageRadioGroupBuyFragment.checkedRadioButtonId == binding.hundredTwoHundredRadioButtonBuyFragment.id) return "100-200"
        else if(binding.numberOfPageRadioGroupBuyFragment.checkedRadioButtonId == binding.twoHundredThreeHundredRadioButtonBuyFragment.id) return "200-300"
        else if(binding.numberOfPageRadioGroupBuyFragment.checkedRadioButtonId == binding.moreThanThreeHundredRadioButtonBuyFragment.id) return "300'den Fazla"
        else return ""
    }

    private fun getComment(): String{
        return binding.commentTextBuyFragment.text.toString()
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.layoutContainerFragment, fragment).commit()
    }

}