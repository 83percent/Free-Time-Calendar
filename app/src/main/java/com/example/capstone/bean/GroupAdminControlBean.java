package com.example.capstone.bean;

public class GroupAdminControlBean {
    private String admin, id;

    public GroupAdminControlBean(String admin, String id) {
        this.admin = admin;
        this.id = id; // 대상이 되는 유저의 고유 코드
    }
    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getAdmin() {
        return admin;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
