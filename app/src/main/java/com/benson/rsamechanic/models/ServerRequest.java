package com.benson.rsamechanic.models;


public class ServerRequest {

    private String operation;
    private User user;
    private Location location;

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
