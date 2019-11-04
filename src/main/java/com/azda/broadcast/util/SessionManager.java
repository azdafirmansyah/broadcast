package com.azda.broadcast.util;

import com.azda.broadcast.model.Users;

public class SessionManager {
    private static SessionManager instance = new SessionManager();

    private Users user = null;

    protected SessionManager (){

    }

    public static SessionManager getInstance(){
        return instance;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
