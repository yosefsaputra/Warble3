package edu.utexas.mpc.warble3.model.user;

import java.sql.ResultSet;

public class User {
    public static final String TAG = "User";

    private String username;
    private String password;
    private String emailAddress;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public static Boolean validateUsername(String username) {
        Boolean valid = true;
        if (username.equals("")) {
            valid = false;
        }
        return valid;
    }
}
