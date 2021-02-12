package net.ideabreed.ibparking.model; /*
 * Created by Rajan Karki on 2/7/21
 * Copyright @2021
 */

import androidx.annotation.NonNull;

public class User {
    private String username;
    private String password;
    private Boolean isLogin;
    

    public User(String username, String password, Boolean isLogin) {
        this.username = username;
        this.password = password;
        this.isLogin = isLogin;
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

    public Boolean getLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }

    @NonNull
    @Override
    public String toString() {
        return "User: " + this.username + " => " + this.isLogin;
    }
}