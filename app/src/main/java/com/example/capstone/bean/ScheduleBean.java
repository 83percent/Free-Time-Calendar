package com.example.capstone.bean;

public class ScheduleBean {
    private String sYear, sMonth, sDay, sHour, sMin;
    private String eYear, eMonth, eDay, eHour, eMin;
    private String name;

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }
    public void setsYear(String sYear) { this.sYear = sYear; }
    public String getsYear() { return sYear; }
    public void setsMonth(String sMonth) { this.sMonth = sMonth; }
    public String getsMonth() { return sMonth; }
    public void setsDay(String sDay) { this.sDay = sDay; }
    public String getsDay() { return sDay; }
    public void setsHour(String sHour) { this.sHour = sHour; }
    public String getsHour() { return sHour; }
    public void setsMin(String sMin) { this.sMin = sMin; }
    public String getsMin() { return sMin; }
    public void seteYear(String eYear) { this.eYear = eYear; }
    public String geteYear() { return eYear; }
    public void seteMonth(String eMonth) { this.eMonth = eMonth; }
    public String geteMonth() { return eMonth; }
    public void seteDay(String eDay) { this.eDay = eDay; }
    public String geteDay() { return eDay; }
    public void seteHour(String eHour) { this.eHour = eHour; }
    public String geteHour() { return eHour; }
    public void seteMin(String eMin) { this.eMin = eMin; }
    public String geteMin() { return eMin; }
}
