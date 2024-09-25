package com.backend;

import com.models.User;

public class SessionManager {
    private static SessionManager instance;
    private User currentUser = new User("default", "default", "default", "default", false);

    // Get the instance of the SessionManager
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Set and get current user
    public User getUser() {
        return currentUser;
    }
    public void setUser(User user) {
        this.currentUser = user;
    }
    public void clearAuthentication() {
        currentUser = new User("default", "default", "default", "default", false);
    }
}
