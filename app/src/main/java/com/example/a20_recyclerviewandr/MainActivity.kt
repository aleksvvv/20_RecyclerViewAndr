package com.example.a20_recyclerviewandr

import android.net.wifi.p2p.WifiP2pManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a20_recyclerviewandr.databinding.ActivityMainBinding
import com.example.a20_recyclerviewandr.model.User
import com.example.a20_recyclerviewandr.model.UsersListener
import com.example.a20_recyclerviewandr.model.UsersService
import com.example.a20_recyclerviewandr.screens.UsersListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, UsersListFragment()).commit()
        }
    }

}