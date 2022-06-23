package com.epitech.cashmanager.nfc

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epitech.cashmanager.R

class NFCFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createDialog("N/I")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nfc, container, false)
    }

    private fun createDialog(text: String?) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(text)
            .setPositiveButton("ok") { dialogInterface, i -> dialogInterface.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }
}