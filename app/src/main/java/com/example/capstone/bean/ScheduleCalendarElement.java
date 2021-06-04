package com.example.capstone.bean;

import java.util.Date;

public class ScheduleCalendarElement {
    private String name, memo, _id;
    private Date start, end;

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMemo() {
        return memo;
    }

    public void setStart(Date startDay) {
        this.start = startDay;
    }

    public Date getStart() {
        return start;
    }

    public void setEnd(Date endDay) {
        this.end = endDay;
    }

    public Date getEnd() {
        return end;
    }
}
