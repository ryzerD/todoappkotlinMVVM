package com.example.todoapp.addTask.ui.model

data class TaskModel(
    val id : Long = System.currentTimeMillis(),
    val task: String,
    var selected: Boolean = false,
)