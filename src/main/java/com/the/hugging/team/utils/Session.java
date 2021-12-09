package com.the.hugging.team.utils;

import com.the.hugging.team.entities.Room;
import com.the.hugging.team.entities.User;

public final class Session {

    private static Session instance;

    private User user;
    private Room selectedRoom;

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

    public Room getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(Room selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    public void cleanSession() {
        user = null;
        selectedRoom = null;
    }
}

