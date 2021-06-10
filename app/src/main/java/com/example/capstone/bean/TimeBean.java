package com.example.capstone.bean;

public class TimeBean {
    private int sYear, sMonth, sDay, sHour, sMin;
    private int eYear, eMonth, eDay, eHour, eMin;
    private String code, type, name;

    public void setStart(int[] starts) {
        this.sYear = starts[0];
        this.sMonth = starts[1];
        this.sDay = starts[2];
        this.sHour = starts[3];
        this.sMin = starts[4];
    }
    public void setEnd(int[] ends) {
        this.eYear = ends[0];
        this.eMonth = ends[1];
        this.eDay = ends[2];
        this.eHour = ends[3];
        this.eMin = ends[4];
    }
    public void setStart(int year, int month, int day, int hour, int min) {
        this.sYear = year;
        this.sMonth = month;
        this.sDay = day;
        this.sHour = hour;
        this.sMin = min;
    }
    public void setEnd(int year, int month, int day, int hour, int min) {
        this.eYear = year;
        this.eMonth = month;
        this.eDay = day;
        this.eHour = hour;
        this.eMin = min;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {this.code = code;}
    public String getCode() { return code; }
    public void setType(String type) {this.type = type;}
    public String getType() { return type; }
    public void setsYear(int sYear) { this.sYear = sYear; }
    public int getsYear() { return sYear; }
    public void setsMonth(int sMonth) { this.sMonth = sMonth; }
    public int getsMonth() { return sMonth; }
    public void setsDay(int sDay) { this.sDay = sDay; }
    public int getsDay() { return sDay; }
    public void setsHour(int sHour) { this.sHour = sHour; }
    public int getsHour() { return sHour; }
    public void setsMin(int sMin) { this.sMin = sMin; }
    public int getsMin() { return sMin; }
    public void seteYear(int eYear) { this.eYear = eYear; }
    public int geteYear() { return eYear; }
    public void seteMonth(int eMonth) { this.eMonth = eMonth; }
    public int geteMonth() { return eMonth; }
    public void seteDay(int eDay) { this.eDay = eDay; }
    public int geteDay() { return eDay; }
    public void seteHour(int eHour) { this.eHour = eHour; }
    public int geteHour() { return eHour; }
    public void seteMin(int eMin) { this.eMin = eMin; }
    public int geteMin() { return eMin; }
}
