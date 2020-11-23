package com.example.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//Inisialisasi Variable
    EditText editText;
    Button btnAdd,btnReset;
    RecyclerView recyclerView;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Penentuan Variable
        editText = findViewById(R.id.edit_text);
        btnAdd = findViewById(R.id.btn_add);
        btnReset = findViewById(R.id.btn_reset);
        recyclerView = findViewById(R.id.recycler_view);

        //Initialize Database
        database = RoomDB.getInstance(this);
        //Strore database value in data list
        dataList = database.mainDao().getAll();

        //Initialize linear Layout Manager
        linearLayoutManager = new LinearLayoutManager( this);
        //Set Layout Manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //Initialize Adapter
        adapter = new MainAdapter(MainActivity.this,dataList);
        //Set Adapter
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get String from edit text
                String stext = editText.getText().toString().trim();
                //Check Condition
                if (!stext.equals("")){
                    //When text is not empty
                    //Initialize main data
                    MainData data = new MainData();
                    //Set text on main data
                    data.setText(stext);
                    //Insert text in database
                    database.mainDao().insert(data);
                    //Clear edit text
                    editText.setText("");
                    //Notify when data is started
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete all data from database
                database.mainDao().reset(dataList);
                //Notify when all data deleted
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });
    }
}