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
                "code char(24) not null unique, " + // 0
                "id INTEGER not null primary key autoincrement," + // 1
                "type varchar default 'free'," + // 2
                "startYear INTEGER not null," + // 3
                "startMonth INTEGER not null," + // 4
                "startDay INTEGER not null," + // 5
                "startHour INTEGER not null," + // 6
                "startMin INTEGER not null," + // 7
                "endYear INTEGER not null," + // 8
                "endMonth INTEGER not null," + // 9
                "endDay INTEGER not null," + // 10
                "endHour INTEGER not null," + // 11
                "endMin INTEGER not null)"); // 12

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
