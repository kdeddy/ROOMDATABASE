package com.example.roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//Menambahkan entitas database
@Database(entities = {MainData.class},version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    //Create database Instance
    private static RoomDB database;
    //Define database Name
    private static String DATABASE_NAME = "database";

    public synchronized static RoomDB getInstance(Context context){
        //Check condition
        if (database == null){
            //When database id null
            //initialize database
            database = Room.databaseBuilder(context.getApplicationContext()
                    ,RoomDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        //Return Database
        return database;
    }
    //Create Dao
    public abstract MainDao mainDao();
}
