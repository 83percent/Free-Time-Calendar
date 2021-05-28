package com.example.capstone.bean;

public class VoteBean {
    private String id;
    private boolean agree;

    public VoteBean(String id, boolean agree) {
        this.id = id;
        this.agree = agree;
    }
    public void setId(String id) { this.id = id; }
    public String getId() { return id; }
    public void setAgree(boolean agree) { this.agree = agree; }
    public boolean getAgree() {return this.agree;}
}
