package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.todoapp.Adapter.ToDoAdapter;
import com.example.todoapp.Model.ToDoModel;
import com.example.todoapp.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{

    private DatabaseHandler db;

    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private FloatingActionButton fab;

    private List<ToDoModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        db = new DatabaseHandler(this);
        db.openDatabase();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db,MainActivity.this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        fab = findViewById(R.id.fab);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);

        tasksAdapter.setTasks(taskList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}

//public class MainActivity extends AppCompatActivity implements DialogCloseListener{
//
//    private RecyclerView taskRecyclerView;
//    private ToDoAdapter tasksAdapter;
//    private FloatingActionButton fab;
//    private List<ToDoModel> taskList;
//    private DatabaseHandler db;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();
//        taskList=new ArrayList<>();
//        db=new DatabaseHandler(this);
//        fab=findViewById(R.id.fab);
//        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
//        itemTouchHelper.attachToRecyclerView(taskRecyclerView);
//        db.openDatabase();
//
//        taskRecyclerView=findViewById(R.id.tasksRecyclerView);
//        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        tasksAdapter=new ToDoAdapter(db,this);
//        taskRecyclerView.setAdapter(tasksAdapter);
//
//
//        taskList=db.getAllTasks();
//        Collections.reverse(taskList);
//        tasksAdapter.setTasks(taskList);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
//            }
//        });
//
//
////        ToDoModel task=new ToDoModel();
////        task.setTask("This is a test task");
////        task.setStatus(0);
////        task.setId(1);
////
////        taskList.add(task);
////        taskList.add(task);
////        taskList.add(task);
////        taskList.add(task);
////        taskList.add(task);
////
////        tasksAdapter.setTasks(taskList);
//
//    }
//    @Override
//    public void handleDialogClose(DialogInterface dialog){
//        taskList=db.getAllTasks();
//        Collections.reverse(taskList);
//        tasksAdapter.setTasks(taskList);
//        tasksAdapter.notifyDataSetChanged();
//    }
//}
//LEO GOAT