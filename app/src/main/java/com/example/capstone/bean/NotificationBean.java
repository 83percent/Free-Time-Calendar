package com.example.capstone.bean;

public class NotificationBean {
    private String type, message1, message2, access;
    // access : 알람을 보낸 Mongo 의 _id

    public void setAccess(String access) {
        this.access = access;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMessage1(String message) {
        this.message1 = message;
    }

    public void setMessage2(String message) {
        this.message2 = message;
    }


    public String getAccess() {
        return access;
    }

    public String getType() {
        return type;
    }

    public String getMessage1() {
        return message1;
    }

    public String getMessage2() {
        return message2;
    }
}

