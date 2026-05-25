package com.example.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = TaskDatabase.getDatabase(application).taskDao()
    val allTasks: Flow<List<Task>> = dao.getAllTasks()

    fun addTask(title: String) {
        viewModelScope.launch {
            dao.insertTask(Task(title = title))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            dao.deleteTask(task)
        }
    }

    fun toggleTask(task: Task) {
        viewModelScope.launch {
            dao.updateTask(task.copy(isDone = !task.isDone))
        }
    }
}
