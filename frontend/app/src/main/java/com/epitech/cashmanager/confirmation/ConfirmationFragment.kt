package com.epitech.cashmanager.confirmation

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.epitech.cashmanager.R
import com.epitech.cashmanager.databinding.FragmentConfirmationBinding

class ConfirmationFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        val binding = DataBindingUtil.inflate<FragmentConfirmationBinding>(inflater, R.layout.fragment_confirmation, container, false)


        binding.ValidateBill.setOnClickListener{
            if(binding.TotalBill.text == "Empty"){
                //createDialog()
                NavHostFragment.findNavController(this).navigate(R.id.action_confirmationFragment_to_paymentFragment)
            }else{
                NavHostFragment.findNavController(this).navigate(R.id.action_confirmationFragment_to_paymentFragment)
            }
        }


        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                ||super.onOptionsItemSelected(item)
    }
    private fun createDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Empty cart")
            .setPositiveButton("ok") { dialogInterface, i -> dialogInterface.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }
}