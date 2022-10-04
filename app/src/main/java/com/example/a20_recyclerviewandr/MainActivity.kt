package com.example.a20_recyclerviewandr

import android.net.wifi.p2p.WifiP2pManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a20_recyclerviewandr.databinding.ActivityMainBinding
import com.example.a20_recyclerviewandr.model.User
import com.example.a20_recyclerviewandr.model.UsersListener
import com.example.a20_recyclerviewandr.model.UsersService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UsersAdapter

    //доступ к модели UsersService через get
    private val usersService: UsersService
        get() = (applicationContext as App).usersService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//передадаим в UsersAdapter
        adapter = UsersAdapter(object: UserActionListener {
            override fun onUserMove(user: User, moveBy: Int){
                usersService.moveUser(user, moveBy)
            }
            override fun OnUserDelete(user: User){
                usersService.deleteUser(user)
            }
            override fun onUserDetails(user: User){
                Toast.makeText(this@MainActivity, "User: ${user.name}", Toast.LENGTH_LONG).show()
            }
        })

        //создаем лаяут менеджер для отражения списка
        val layoutManager = LinearLayoutManager(this)

        //назначаем его нашей рецаклер вью
        binding.recyclerView.layoutManager = layoutManager
        Log.d("MyLog", "layoutManager $layoutManager")

        //назначаем ADAPTER нашей рецаклер вью
        binding.recyclerView.adapter = adapter
        Log.d("MyLog", "layoutManager2 $layoutManager")

        //добавляем слушатель
        usersService.addListener(usersListener)
        Log.d("MyLog", "addListener")
    }
    //для предотвращения утечки памяти удаляем слушатель
    override fun onDestroy() {
        super.onDestroy()
        usersService.removeListener (usersListener)
        Log.d("MyLog", "Destroy")
    }

    //слушатель, который будет слушать изменения в
    private val usersListener: UsersListener = {
        adapter.users = it
    }
}