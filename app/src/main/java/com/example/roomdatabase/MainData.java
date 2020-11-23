package com.example.roomdatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//Mendefinikan Nama Tabel
@Entity(tableName = "table_name")
public class MainData implements Serializable {
    //Membuat Id Kolom
    @PrimaryKey(autoGenerate = true)
    private int ID;

    //Membuat Text Kolom
    @ColumnInfo(name = "text")
    private String text;

    //Generate getter and setter

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
