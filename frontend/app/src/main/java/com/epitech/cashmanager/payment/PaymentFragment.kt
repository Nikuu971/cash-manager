package com.epitech.cashmanager.payment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.epitech.cashmanager.R
import com.epitech.cashmanager.databinding.FragmentPaymentBinding


class PaymentFragment : Fragment() {

    private lateinit var binding : FragmentPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false)


        binding.CreditCardNFCButton.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_paymentFragment_to_NFCFragment)
        }
        binding.ChequeQRCButton.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_paymentFragment_to_QRCodeFragment)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    //MENU AND NAV
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                ||super.onOptionsItemSelected(item)
    }
    //END

}