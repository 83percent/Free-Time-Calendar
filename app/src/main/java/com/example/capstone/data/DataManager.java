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
                "code char(24) not null primary key," +
                "name varchar(20) not null," +
                "foreign key(code) references timeTBL(code))");

        // Group
        db.execSQL("CREATE TABLE groupTBL(" +
                "groupCode char(24) not null primary key," + // mongoDB _id
                "name varchar(20) not null," +
                "admin char(24) not null," +
                "length INTEGER default 1)");

        // Notification
        db.execSQL("CREATE TABLE notification (" +
                "id INTEGER not null primary key autoincrement," +
                "owner char(24) not null," +
                "type varchar not null," +
                "message varchar" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS timeTBL");
        db.execSQL("DROP TABLE IF EXISTS scheduleNameTBL");
        db.execSQL("DROP TABLE IF EXISTS groupTBL");
        db.execSQL("DROP TABLE IF EXISTS groupMemberTBL");
        db.execSQL("DROP TABLE IF EXISTS notification");
    }
}
