package com.amador.pruebaexamen;

/**
 * Created by amador on 11/12/16.
 */

public class User {

    private String userName;
    private String userMail;
    private static User user;

    public static User getUser(){

        if(user == null){

            user = new User();
        }

        return user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    private User() {

        this.userName = null;
        this.userMail = null;
    }
}
