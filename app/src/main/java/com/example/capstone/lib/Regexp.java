package com.example.capstone.lib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regexp {
    public boolean email(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) { err = true; }
        return err;
    }
    public boolean password(String password) {
        // 8자 ~ 20자 이하
        if(password.length() > 7 && password.length() < 21) return true;
        else return false;
    }
}
