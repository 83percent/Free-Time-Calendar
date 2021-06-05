package com.example.capstone.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.capstone.bean.NotificationBean;


/*
    Data Format
    id INTEGER autoincrement
    owner char(24) not null
    type varchar not null
    message varchar not null
 */

public class NotificationData {
    private DataManager db;
    private static NotificationData instance;
    private NotificationData(Context context) {
        db = loadDataBase(context);
    }
    public static NotificationData getInstance(Context context) {
        if(instance == null) instance = new NotificationData(context);
        return instance;
    }
    private DataManager loadDataBase(Context context) {return new DataManager(context);}

    // Create
    // 1개
    public boolean set(String owner, NotificationBean bean) {
        SQLiteDatabase sql = db.getWritableDatabase();
        try {
            sql.execSQL("INSERT INTO notification(owner, type, message) VALUES (" +
                    "'" +owner+"','" + bean.getType() + "','" + bean.getMessage() + "'" +
                    ")");
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(sql != null) sql.close();
        }
    }
    // 복수개
    public boolean set(String owner, NotificationBean[] beans) {
        SQLiteDatabase sql = db.getWritableDatabase();
        try {
            int count = beans.length;
            for(int i=0; i<count; i++) {
                sql.execSQL("INSERT INTO notification(owner, type, message) VALUES (" +
                        "'" +owner+"','" + beans[i].getType() + "','" + beans[i].getMessage() + "'" +
                        ")");
            }
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(sql != null) sql.close();
        }
    }

    // Read
    public NotificationBean[] get(String id, int startCount) {
        SQLiteDatabase sql = db.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sql.rawQuery("SELECT id, type, message FROM notification WHERE onwer = '"+id+"' AND id >"+startCount +" LIMIT 20", null);
            if(cursor != null) {
                cursor.moveToLast();
                int readLength = cursor.getPosition();
                if(readLength > 0) {
                    NotificationBean[] beans = new NotificationBean[readLength+1];
                    NotificationBean bean = null;
                    for(int i=0; i<readLength+1; ++i) {
                        bean = new NotificationBean();
                        bean.setId(cursor.getString(0));
                        bean.setType(cursor.getString(1));
                        bean.setType(cursor.getString(2));

                        beans[i] = bean;
                    }
                    return beans;
                }
            }
            return null;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(cursor != null) cursor.close();
            if(sql != null) sql.close();
        }
    }

    // Delete
    public boolean delete(String id) {
        SQLiteDatabase sql = db.getWritableDatabase();
        try {
            sql.execSQL("DELETE FROM notification WHERE owner ='"+id+"'");
            return true;
        } catch(Exception e) {
            return false;
        } finally {
            if(sql != null) sql.close();
        }
    }
}
