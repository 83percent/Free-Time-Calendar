package com.example.capstone.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataManager extends SQLiteOpenHelper {
    public DataManager(Context context) {
        super(context, "FREETIME_DB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // User Time Table
        db.execSQL("CREATE TABLE timeTBL (" +
                "code char(24) not null unique, " +
                "id INTEGER not null primary key autoincrement," +
                "type varchar default 'free'," +
                "startYear INTEGER not null," +
                "startMonth INTEGER not null," +
                "startDay INTEGER not null," +
                "startHour INTEGER not null," +
                "startMin INTEGER not null," +
                "endYear INTEGER not null," +
                "endMonth INTEGER not null," +
                "endDay INTEGER not null," +
                "endHour INTEGER not null," +
                "endMin INTEGER not null)");

        // Schedule Time
        db.execSQL("CREATE TABLE scheduleNameTBL (" +
                "id INTEGER not null primary key," +
                "name varchar(20) not null," +
                "foreign key(id) references time(id))");

        // Group
        db.execSQL("CREATE TABLE groupTBL(" +
                "code char(24) not null primary key," +
                "name varchar(20) not null," +
                "length INTEGER default 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
