package com.example.todoapp.addTask.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.todoapp.addTask.ui.model.TaskModel

@Composable
fun TaskScreen(taskViewModel: TaskViewModel) {
    val showDialog: Boolean by taskViewModel.showDialog.observeAsState(false)
    Box(modifier = Modifier.fillMaxSize()) {
        FbAddTask(Modifier.align(Alignment.BottomEnd), taskViewModel)
        DialogAddTask(
            showDialog,
            onDissmis = { taskViewModel.onDialogClose() },
            onTaskAdd = { taskViewModel.onTaskAdd(it) })
        TaskList(taskViewModel)

    }
}

@Composable
fun TaskList(taskViewModel: TaskViewModel) {
    val myTask: List<TaskModel> = taskViewModel.task
    LazyColumn {
        items(myTask, key = { it.id }) {
            itemTask(taskModel = it, taskViewModel = taskViewModel)
        }
    }
}


@Composable
fun itemTask(taskModel: TaskModel, taskViewModel: TaskViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                        taskViewModel.onItemRemove(taskModel)
                    }
                )
            }
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = taskModel.task, modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
            Checkbox(
                checked = taskModel.selected,
                onCheckedChange = { taskViewModel.onCheckBoxSelected(taskModel) })
        }
    }

}


@Composable
fun FbAddTask(modifier: Modifier, taskViewModel: TaskViewModel) {
    FloatingActionButton(onClick = {
        taskViewModel.onDialogShow()
    }, modifier = modifier.padding(16.dp)) {
        Icon(Icons.Filled.Add, contentDescription = "")
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogAddTask(show: Boolean, onDissmis: () -> Unit, onTaskAdd: (String) -> Unit) {
    var myTask by remember { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = { onDissmis() }) {
            Card(

            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    Text(
                        text = "Añade tu tarea",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    TextField(
                        value = myTask,
                        onValueChange = { myTask = it },
                        maxLines = 1,
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.size(16.dp))

                    Button(
                        onClick = {
                            onTaskAdd(myTask)
                            myTask = ""
                        }, modifier = Modifier.align(Alignment.CenterHorizontally),
                        enabled = myTask.isNotEmpty()
                    ) {
                        Text(text = "Añadir tu tarea")
                    }

                }
            }
        }
    }
}