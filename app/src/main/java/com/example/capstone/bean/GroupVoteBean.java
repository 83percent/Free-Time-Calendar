package com.example.capstone.bean;

public class GroupVoteBean {
    private String name, start, end, minLength, reg_id;

    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }
    public String getMinLength() {
        return minLength;
    }
    public void setEnd(String end) {
        this.end = end;
    }
    public void setEnd(int[] end) {
        this.end = end[0]+"-"+end[1]+"-"+end[2]+" "+end[3]+":"+end[4];
    }
    public void setStart(String start) {
        this.start = start;
    }
    public void setStart(int[] start) {
        this.start = start[0]+"-"+start[1]+"-"+start[2]+" "+start[3]+":"+start[4];
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEnd() {
        return end;
    }
    public String getStart() {
        return start;
    }
    public String getName() {
        return name;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public String getReg_id() {
        return reg_id;
    }
}
