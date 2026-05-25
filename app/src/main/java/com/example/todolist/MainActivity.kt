package com.example.todolist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = TaskAdapter(
            onDelete = { viewModel.deleteTask(it) },
            onToggle = { viewModel.toggleTask(it) }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.allTasks.collect { tasks ->
                adapter.submitList(tasks)
            }
        }

        binding.btnAdd.setOnClickListener {
            val text = binding.etTask.text.toString().trim()
            if (text.isNotEmpty()) {
                viewModel.addTask(text)
                binding.etTask.setText("")
            } else {
                Toast.makeText(this, "اكتب مهمة أولاً", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
