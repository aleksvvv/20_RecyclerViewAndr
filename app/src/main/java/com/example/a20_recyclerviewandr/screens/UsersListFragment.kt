package com.example.a20_recyclerviewandr.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a20_recyclerviewandr.UserActionListener
import com.example.a20_recyclerviewandr.UsersAdapter
import com.example.a20_recyclerviewandr.databinding.FragmentUserListBinding
import com.example.a20_recyclerviewandr.model.User

class UsersListFragment: Fragment() {
    private lateinit var binding: FragmentUserListBinding
    private lateinit var adapter: UsersAdapter

    //получаем доступ к вью модели
    private val viewModel: UsersListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserListBinding.inflate(inflater,container,false)

        adapter = UsersAdapter(object : UserActionListener{
            override fun onUserMove(user: User, moveBy: Int) {
                viewModel.moveUser(user, moveBy)
            }

            override fun OnUserDelete(user: User) {
                viewModel.deleteUser(user)
            }

            override fun onUserDetails(user: User) {
                            }

            override fun onUserFire(user: User) {
                TODO("Not yet implemented")
            }

        })
        //подпишемся на данные из вьюмодели
        viewModel.users.observe(viewLifecycleOwner, Observer {
            adapter.users = it
        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        return binding.root
    }

}