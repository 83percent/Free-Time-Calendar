package com.example.capstone.bean;

public class SignUpBean {
    private String email;
    private String password;
    private String name;

    public SignUpBean(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public void setEmail(String email) { this.email = email; }
    public void setPasssword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public String getPasssword() { return password; }
    public String getName() { return name; }
}
