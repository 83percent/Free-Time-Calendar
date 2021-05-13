package com.example.capstone.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.capstone.bean.IDReturnBean;
import com.example.capstone.bean.TimeBean;
import com.example.capstone.connect.RetrofitConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeData {
    private String id;
    private Context context;
    private SQLiteDatabase sql = null;
    DataManager db = null;

    public TimeData(String id ,Context context) {
        this.context = context;
        this.id = id;
        db = loadDataBase(context);
    }
    // DB 가져오기
    private DataManager loadDataBase(Context context) {
        return new DataManager(context);
    }

    /**
     * 데이터 추가
     */
    private boolean __insert(String code, TimeBean bean) {
        try {
            Log.d("Connect Code", "__insert: " + code);
            sql = db.getWritableDatabase();
            sql.execSQL("INSERT INTO TIMETBL(code, type, startYear, startMonth, startDay, startHour, startMin," +
                    " endYear, endMonth, endDay, endHour, endMin) VALUES ('"+code+"', 'free',"+bean.getsYear()+", " +
                    bean.getsMonth()+", "+bean.getsDay()+", "+bean.getsHour()+", "+bean.getsMin()+", " +
                    bean.geteYear()+", "+bean.geteMonth()+", "+bean.geteDay()+", "+bean.geteHour()+", "+bean.geteMin()+")");
            return true;
        } catch(SQLException se) {
            se.printStackTrace();
            return false;
        } finally {
            sql.close();
            getAll();
        }

    }
    public void set(final TimeBean bean) {
        RetrofitConnection retrofitConnection = RetrofitConnection.getInstance();
        Call<String> call = retrofitConnection.server.sendFree(this.id, bean);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                /*
                    1. 겹치는 시간 데이터 있는지 확인 ( 없어도 될 것 같음)
                    2. 서버에 저장 하고 code 값 받기
                    3. code 값을 가지고 sqlite 에 저장
                 */
                if(response.code() == 200) {
                    String responseID = response.body();
                    if(__insert(responseID, bean)) {
                        Toast.makeText(context, "추가 성공", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "추가 실패", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "추가 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) { }
        });
    }
    /**
     * 데이터 불러오기
     */
    public void getAll() {
        sql = db.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sql.rawQuery("SELECT * FROM timeTBL ", null);
            while(cursor.moveToNext()) {
                Log.d("getData", "getAll: " + cursor.getString(0));
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(cursor == null) cursor.close();
            sql.close();
        }


    }
}
