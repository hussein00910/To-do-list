package com.example.todolist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemTaskBinding

class TaskAdapter(
    private val onDelete: (Task) -> Unit,
    private val onToggle: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DiffCallback) {

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.tvTask.text = task.title

            if (task.isDone) {
                binding.tvTask.paintFlags =
                    binding.tvTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvTask.alpha = 0.5f
            } else {
                binding.tvTask.paintFlags =
                    binding.tvTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.tvTask.alpha = 1.0f
            }

            binding.cbDone.isChecked = task.isDone
            binding.cbDone.setOnClickListener { onToggle(task) }
            binding.btnDelete.setOnClickListener { onDelete(task) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    }
}
