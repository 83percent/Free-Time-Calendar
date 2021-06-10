package com.example.capstone.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
            sql.execSQL("INSERT INTO notification(access, owner, type, message1, message2) VALUES (" +
                    "'"+bean.getAccess()+"', '" +owner+"','" + bean.getType() + "','" + bean.getMessage1() + "','" + bean.getMessage2() + "'" +
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

                sql.execSQL("INSERT INTO notification(access, owner, type, message1, message2) VALUES (" +
                        "'"+beans[i].getAccess()+"' ,'" +owner+"','" + beans[i].getType() + "','" + beans[i].getMessage1() + "','" + beans[i].getMessage2() + "'" +
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
    public NotificationBean[] get(String id) {
        SQLiteDatabase sql = db.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sql.rawQuery("SELECT id, access, type, message1, message2 FROM notification WHERE owner = '"+id+"' ORDER BY id DESC", null);
            /*
            if(startCount != 0) {
                cursor = sql.rawQuery("SELECT access, type, message1, message2 FROM notification WHERE owner = '"+id+"' AND id <"+startCount +" LIMIT 20 ORDER BY id DESC", null);
            } else {

                //cursor = sql.rawQuery("SELECT id, access, type, message1, message2 FROM notification WHERE owner = '"+id+"'", null);
            }

             */

            if(cursor != null) {
                cursor.moveToLast();
                int readLength = cursor.getPosition();
                if(readLength > 0) {
                    NotificationBean[] beans = new NotificationBean[readLength+1];
                    NotificationBean bean = null;
                    cursor.moveToFirst();
                    for(int i=0; i<readLength+1; ++i) {
                        bean = new NotificationBean();
                        bean.setAccess(cursor.getString(1));

                        bean.setType(cursor.getString(2));
                        bean.setMessage1(cursor.getString(3));
                        bean.setMessage2(cursor.getString(4));

                        cursor.moveToNext();
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
