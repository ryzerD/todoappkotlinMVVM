package com.example.todoapp.addTask.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.addTask.ui.model.TaskModel
import javax.inject.Inject


class TaskViewModel @Inject constructor() : ViewModel() {


    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog : LiveData<Boolean> = _showDialog

    private val _tasks = mutableStateListOf<TaskModel>()
    val task: List <TaskModel> = _tasks

    fun onDialogClose() {
       _showDialog.value = false
    }

    fun onDialogShow() {
        _showDialog.value = true
    }

    fun onTaskAdd(it:String) {
        _showDialog.value = false
        _tasks.add(TaskModel(task = it))
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
        val index = _tasks.indexOf(taskModel)
        _tasks[index] = _tasks[index].let {
            it.copy(selected = !it.selected)
        }
    }

    fun onItemRemove(taskModel: TaskModel) {
        val task = _tasks.find { it.id == taskModel.id }
        _tasks.remove(task)
    }


}