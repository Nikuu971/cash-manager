package com.epitech.cashmanager.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.epitech.cashmanager.R
import com.epitech.cashmanager.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding : FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.SubmitButtonLogin.setOnClickListener {
            userLogin()
        }

        binding.SubmitServerConnection.setOnClickListener {
            setServerURL()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun userLogin(){

    }
    private fun setServerURL(){

    }
}