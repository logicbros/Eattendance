package com.eat.parse.eattendance;

/**
 * Created by ronak_000 on 1/8/2016.
 */
public class Validatation {
    public Validatation()
    {

    }
    public boolean isEmailValid(String email) {
        //add your own logic
        boolean bool= email.contains("@");
        return bool;
    }

    public boolean isPasswordValid(String password) {
        //add your own logic
        return password.length() > 2;
    }


}
