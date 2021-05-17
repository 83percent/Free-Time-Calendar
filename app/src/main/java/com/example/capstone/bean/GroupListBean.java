package com.example.capstone.bean;

public class GroupListBean {
    private String id, name;
    private int scheduleCount, memberCount;

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setScheduleCount(int scheduleCount) {
        this.scheduleCount = scheduleCount;
    }

    public int getScheduleCount() {
        return scheduleCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public int getMemberCount() {
        return memberCount;
    }
}
