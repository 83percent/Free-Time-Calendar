package com.example.capstone.bean;

public class CreateGroupBean {
    private String name, admin;
    public CreateGroupBean(String name, String admin) {
        this.name = name;
        this.admin = admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getAdmin() {
        return admin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
