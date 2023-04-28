package com.example.buku.View.MainFragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.buku.R
import com.example.buku.databinding.FragmentContainerBinding


class ContainerFragment : Fragment() {

    private lateinit var binding: FragmentContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        loadFragment(HomeFragment())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home -> {loadFragment(HomeFragment())}
            R.id.sell -> {loadFragment(SellFragment())}
            R.id.sold -> {loadFragment(SoldFragment())}
            R.id.bought -> {loadFragment(BoughtFragment())}
            R.id.profile -> {loadFragment(ProfileFragment())}
            R.id.sellToSystem -> {loadFragment(SellFragment())}
            R.id.setPassword -> {loadFragment(SetPasswordFragment())}
            R.id.logout -> {}
        }
        return true
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.layoutContainerFragment, fragment).commit()
    }
}