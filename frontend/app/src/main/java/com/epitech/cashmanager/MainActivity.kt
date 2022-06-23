package com.epitech.cashmanager

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.epitech.cashmanager.databinding.ActivityMainBinding
import com.epitech.cashmanager.user.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val CAMERA_REQUEST_CODE = 101
    private val AUDIO_REQUEST_CODE = 303
    private val NFC_REQUEST_CODE = 202
    private val TAG = "CAMERA Perm Request"
    private val TAG2 = "NFC Perm Request"
    private val TAG3 = "Audio Perm Request"
    private val TAGAct = "MainActivity"
    private val user = User()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navController = this.findNavController(R.id.nav_host_view)
        NavigationUI.setupActionBarWithNavController(this, navController)
        NavigationUI.setupWithNavController(binding.navView, navController)
        setupPermissions()

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAGAct, "onResume Called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAGAct, "onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAGAct, "onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAGAct, "onDestroy Called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAGAct, "onRestart Called")
    }



    private fun setupPermissions() {
        val permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val permissionAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        val permissionNFC = ContextCompat.checkSelfPermission(this, Manifest.permission.NFC)

        if (permissionCamera != PackageManager.PERMISSION_GRANTED && permissionAudio != PackageManager.PERMISSION_GRANTED && permissionNFC != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), AUDIO_REQUEST_CODE)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.NFC), NFC_REQUEST_CODE )
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_view)
        return navController.navigateUp()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode){
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Log.i(TAG, "Permission has been denied by user")

                } else {
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
            NFC_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Log.i(TAG2, "Permission has been denied by user")

                } else {
                    Log.i(TAG2, "Permission has been granted by user")
                }
            }
            AUDIO_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Log.i(TAG3, "Permission has been denied by user")

                } else {
                    Log.i(TAG3, "Permission has been granted by user")
                }
            }
        }
    }
}