package com.example.roomdatabase

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.adapter.UserAdapter
import com.example.roomdatabase.database.AppDatabase
import com.example.roomdatabase.databinding.ActivityMainBinding
import com.example.roomdatabase.model.User
import com.example.roomdatabase.repository.UserRepository
import com.example.roomdatabase.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

// MainActivity.kt
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var selectedUser: User? = null

    // Sử dụng delegate by viewModels()
    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = UserAdapter { user ->
            selectedUser = user
            binding.nameEditText.setText(user.name)
            binding.emailEditText.setText(user.email)
        }

        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        viewModel.allUsers.observe(this) { users ->
            adapter.submitList(users)
        }

        binding.addButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            if (name.isNotEmpty() && email.isNotEmpty()) {
                viewModel.insert(User(name = name, email = email))
                clearInputs()
            }
        }

        binding.updateButton.setOnClickListener {
            selectedUser?.let { user ->
                val name = binding.nameEditText.text.toString()
                val email = binding.emailEditText.text.toString()
                if (name.isNotEmpty() && email.isNotEmpty()) {
                    viewModel.update(user.copy(name = name, email = email))
                    clearInputs()
                    selectedUser = null
                }
            }
        }
    }

    private fun clearInputs() {
        binding.nameEditText.text.clear()
        binding.emailEditText.text.clear()
    }
}