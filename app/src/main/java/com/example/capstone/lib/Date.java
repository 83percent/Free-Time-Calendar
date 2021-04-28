package com.example.capstone.lib;

import java.util.Calendar;

public class Date {
    private Calendar calendar = null;
    private int __day = 0;
    public Date() {
        calendar = Calendar.getInstance();
        __day = calendar.get(Calendar.DATE);
    }
    public void setDate(int year, int month, int day) {
        calendar.set(Calendar.YEAR, Integer.valueOf(year));
        calendar.set(Calendar.MONTH, Integer.valueOf(month)-1); // 0 ~ 11월이라  1을 빼줌
        calendar.set(Calendar.DAY_OF_MONTH, day); // 지정 월의 날짜
        __day = day;
    }


    public int getYear() {return calendar.get(Calendar.YEAR);}
    public int getMonth() {return calendar.get(Calendar.MONTH)+1;}
    public int getDay() {return calendar.get(Calendar.DATE);}
    public int getActualMaximum() {
        return calendar.getActualMaximum(calendar.DATE);
    }
    /*
        0 : SUN
        6 : SAT
     */
    public int getFirstDayOfWeek() {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(calendar.DAY_OF_WEEK)-1;
    }

    // 달의 마지막 일을 구함
    private int getLastDay() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else if (month == 2 && isLeapYear(year) == true) {
            return 29;
        } else if (month == 2 && isLeapYear(year) == false) {
            return 28;
        } else {
            return 31;
        }
    }

    // 해당 년도가 윤년인지 판별
    private static boolean isLeapYear(int year) { return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)); }
}
