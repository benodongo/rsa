package com.benson.rsamechanic.models;


import org.json.JSONObject;

public class ServerResponse {

    private String result;
    private String message;
    private User user;
    private Location location;
    private JSONObject jsonObject;


    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public Location getLocation() {
        return location;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
