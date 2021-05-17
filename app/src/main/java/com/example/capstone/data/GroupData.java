package com.example.capstone.data;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.capstone.bean.GroupBean;
import com.example.capstone.main.Group;

/*
    기기에 Group Data CRUD 클래스
    ! 싱글톤 클래스
 */
public class GroupData {
    private Context context;
    private SQLiteDatabase sql = null;
    private DataManager db = null;

    private static GroupData instance = null;
    private GroupData(Context context) {
        this.context = context;
        db = loadDataBase(context);
    } // Singleton
    private DataManager loadDataBase(Context context) {
        return new DataManager(context);
    }
    public static GroupData getInstance(Context context) {
        if(instance == null) instance = new GroupData(context);
        return instance;
    }


    /*
        Create
        @params String code     : 그룹 고유 코드
        @params String name       : 그룹명칭
        @params String id       : 생성자 고유 코드
     */
    public boolean set(String code, String name, String id) {
        try {
            sql  = db.getWritableDatabase();
            sql.execSQL("INSERT INTO groupTBL VALUES ('"+code+"','"+name+"','"+id+"', 1)");
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(sql != null) sql.close();
        }
    }

    /*
        Read
     */
    public GroupBean[] getList() {
        sql = db.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sql.rawQuery("SELECT groupCode, name FROM groupTBL", null);
            cursor.moveToLast();
            GroupBean[] groupBeans = new GroupBean[cursor.getPosition()+1];
            if(groupBeans.length > 0) {
                int index = 0;
                cursor.moveToPosition(-1);
                GroupBean bean = null;
                while(cursor.moveToNext()) {
                    bean = new GroupBean();
                    bean.setCode(cursor.getString(0));
                    bean.setGroupName(cursor.getString(1));

                    groupBeans[index] = bean;
                    index++;
                }
                return groupBeans;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(cursor != null) cursor.close();
            if(sql != null) sql.close();
        }
        return null;
    }
    /*
        UPDATE
        @param String groupCode     그룹 고유코드
        @param String newName       새로 바꿀 명칭

        @return boolean
     */
    public boolean updateGroupName(String groupCode, String newName) {
        sql = db.getWritableDatabase();
        try {
            sql.execSQL("UPDATE groupTBL SET name="+newName+" WHERE groupCode="+groupCode, null);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (sql != null) sql.close();
        }
    }
    /*
        DELETE
        그룹탈퇴임, 서버에서 그룹내에 속한 사람이 1명도 없어지면 자동으로 그룹을 삭제함
        @param groupCode        그룹 고유코드

        @return boolean
     */
    public boolean delete(String groupCode) {
        sql = db.getWritableDatabase();
        try {
            sql.execSQL("DELETE FROM groupTBL WHERE groupCode="+groupCode, null);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(sql != null) sql.close();
        }
    }
}
