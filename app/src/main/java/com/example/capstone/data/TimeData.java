package com.example.capstone.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.capstone.bean.FreeBean;
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

    public FreeBean[] get(int year, int month) {
        return get(year,month,-1);
    }
    public FreeBean[] get(int year, int month, int day) {
        sql = db.getReadableDatabase();
        //Log.d("income Data", "get: days : year : " + year + " / Month : " + month + " / Day : " + day);
        Cursor cursor = null;
        try {
            String query;
            if(day > 0) {
                query = "SELECT * FROM TIMETBL WHERE startYear="+year+" AND startMonth="+month+" AND startDay="+day;
            } else {
                query = "SELECT * FROM TIMETBL WHERE startYear="+year+" AND startMonth="+month;
            }
            cursor = sql.rawQuery(query, null);
            cursor.moveToLast();

            FreeBean[] freeBeans = new FreeBean[cursor.getPosition()+1];
            cursor.moveToPosition(-1);
            FreeBean bean = null;
            if(freeBeans.length > 0) {
                for(int i=0; i<freeBeans.length; i++) {
                    cursor.moveToNext();
                    bean = new FreeBean();
                    bean.setCode(cursor.getString(0));
                    bean.setId(cursor.getInt(1));
                    bean.setType(cursor.getString(2));
                    bean.setsDay(cursor.getInt(5));
                    bean.setsHour(cursor.getInt(6));
                    bean.setsMin(cursor.getInt(7));
                    bean.seteDay(cursor.getInt(10));
                    bean.seteHour(cursor.getInt(11));
                    bean.seteMin(cursor.getInt(12));

                    freeBeans[i] = bean;

                }
                return freeBeans;
            } else  return null;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(cursor == null) cursor.close();
            sql.close();
        }
    }
}
