package com.example.capstone.bean;

public class GroupScheduleBean {
    private String name, start, end, memo;
    private int dday;
    public GroupScheduleBean(String name, String start, String end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setDday(int dday) {
        this.dday = dday;
    }

    public int getDday() {
        return dday;
    }
}
