package com.the.hugging.team.utils;

import com.the.hugging.team.entities.User;

public final class Session {

    private static Session instance;

    private User user;

    private Session() {
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void cleanSession() {
        user = null;
    }
}

